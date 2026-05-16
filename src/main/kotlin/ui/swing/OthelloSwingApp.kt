package ui.swing

import model.Game
import model.Player
import repository.JsonMatchRepository
import repository.JsonPlayerRepository
import rules.ClassicRules
import util.GameTimer
import javax.swing.*
import java.awt.*

class OthelloSwingApp {

    private val playerRepo = JsonPlayerRepository()
    private val matchRepo = JsonMatchRepository()
    private var game: Game? = null
    private var boardPanel: BoardPanel? = null
    private var statusLabel: JLabel? = null
    private var timer: GameTimer? = null

    fun createAndShowGUI() {
        val frame = JFrame("Othello")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        val players = playerRepo.findAll().take(2)
            .ifEmpty { listOf(Player(1, "Player 1", 1), Player(2, "Player 2", 2)) }

        game = Game(1, ClassicRules(), players)
        timer = GameTimer()

        val topPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            val playerInfo = JLabel(formatPlayers(players, game!!.currentPlayer))
            statusLabel = JLabel("Ход: ${game!!.currentPlayer.name} | Время: 00:00")
            add(playerInfo)
            add(statusLabel)
        }

        boardPanel = BoardPanel(game!!.board) { x, y -> handleMove(x, y) }

        val bottomPanel = JPanel().apply {
            add(JButton("Реестр игроков").apply {
                addActionListener { showRegistryDialog(frame) }
            })
            add(JButton("Новая игра").apply {
                addActionListener {
                    game = Game(1, ClassicRules(), players)
                    boardPanel?.updateBoard(game!!.board)
                    timer?.reset()
                    statusLabel?.text = "Ход: ${game!!.currentPlayer.name} | Время: 00:00"
                }
            })
        }

        frame.add(topPanel, BorderLayout.NORTH)
        frame.add(boardPanel, BorderLayout.CENTER)
        frame.add(bottomPanel, BorderLayout.SOUTH)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        timer?.setOnTick { secs ->
            val mins = secs / 60
            val s = secs % 60
            statusLabel?.text = "Ход: ${game?.currentPlayer?.name} | Время: %02d:%02d".format(mins, s)
        }
        timer?.start()
    }

    private fun handleMove(x: Int, y: Int) {
        val g = game ?: return
        val result = g.makeMove(g.currentPlayer.id, x, y)
        when (result) {
            model.Result.VALID -> {
                boardPanel?.updateBoard(g.board)
                statusLabel?.text = "Ход: ${g.currentPlayer.name} | ${statusLabel?.text?.substringAfter('|') ?: ""}"
            }
            model.Result.GAME_OVER -> {
                boardPanel?.updateBoard(g.board)
                timer?.stop()
                showGameOver(g.winner)
                saveGameStats(g)
            }
            model.Result.INVALID -> {
                JOptionPane.showMessageDialog(null, "Недопустимый ход", "Ошибка", JOptionPane.WARNING_MESSAGE)
            }
        }
    }

    private fun formatPlayers(players: List<Player>, current: Player): String {
        return players.joinToString(" | ") { p ->
            val mark = if (p.id == current.id) "► " else ""
            "$mark${p.name} (${if (p.color == 1) "●" else "○"}): ${p.wins}W/${p.losses}L"
        }
    }

    private fun showGameOver(winner: Player?) {
        val msg = if (winner != null) "Победитель: ${winner.name}" else "Ничья!"
        JOptionPane.showMessageDialog(null, msg, "Игра окончена", JOptionPane.INFORMATION_MESSAGE)
    }

    private fun saveGameStats(g: Game) {
        val players = g.getPlayers()
        val winnerId = g.winner?.id
        players.forEach { player ->
            val wins = if (player.id == winnerId) player.wins + 1 else player.wins
            val losses = if (player.id != winnerId && winnerId != null) player.losses + 1 else player.losses
            val gamesPlayed = player.gamesPlayed + 1
            playerRepo.save(player.withStats(wins, losses, gamesPlayed))
        }
        val record = model.GameRecord(
            id = g.getId(),
            playerIds = players.map { it.id },
            winnerId = winnerId,
            moves = "",
            blackScore = g.blackScore,
            whiteScore = g.whiteScore,
            date = java.time.LocalDateTime.now().toString()
        )
        matchRepo.save(record)
    }

    private fun showRegistryDialog(parent: Component) {
        val dialog = JDialog(parent as? Frame, "Реестр игроков", true)
        dialog.layout = BorderLayout()
        dialog.size = Dimension(400, 300)

        val listModel = DefaultListModel<Player>()
        playerRepo.findAll().forEach { listModel.addElement(it) }

        val list = JList<Player>(listModel)
        list.cellRenderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(
                list: JList<*>?, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean
            ): Component {
                val text = if (value is Player) {
                    "${value.name} (${if (value.color == 1) "●" else "○"}) - ${value.wins}W/${value.losses}L"
                } else value?.toString() ?: ""
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus)
            }
        }

        val addForm = JPanel(FlowLayout()).apply {
            add(JLabel("ID:"))
            val idField = JTextField(5)
            add(idField)
            add(JLabel("Имя:"))
            val nameField = JTextField(10)
            add(nameField)
            add(JButton("Добавить").apply {
                addActionListener {
                    val id = idField.text.toIntOrNull() ?: return@addActionListener
                    val name = nameField.text.takeIf { it.isNotBlank() } ?: return@addActionListener
                    playerRepo.save(Player(id, name, 1))
                    listModel.addElement(Player(id, name, 1))
                    idField.text = ""
                    nameField.text = ""
                }
            })
        }

        val deleteBtn = JButton("Удалить").apply {
            addActionListener {
                val selected = list.selectedValue ?: return@addActionListener
                val all = playerRepo.findAll().toMutableList()
                all.removeIf { it.id == selected.id }
                all.forEach { playerRepo.save(it) }
                listModel.removeElement(selected)
            }
        }

        dialog.add(addForm, BorderLayout.NORTH)
        dialog.add(JScrollPane(list), BorderLayout.CENTER)
        dialog.add(deleteBtn, BorderLayout.SOUTH)
        dialog.setLocationRelativeTo(parent)
        dialog.isVisible = true
    }
}