import java.util.Scanner

enum class Player(val symbol: Char) {
    X('X'), O('O');

    fun other() = if (this == X) O else X
}

fun printMatrix(cells: Array<Player?>) {
    println("---------")
    for (r in 0..2) {
        print("| ")
        for (c in 0..2) {
            val cell = cells[r * 3 + c]
            print(cell?.symbol ?: ' ')
            if (c < 2) print(' ')
        }
        println(" |")
    }
    println("---------")
}

val lines = arrayOf(
    intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
    intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
    intArrayOf(0,4,8), intArrayOf(2,4,6)
)

fun hasWinner(cells: Array<Player?>): Player? {
    for (line in lines) {
        val a = cells[line[0]]
        val b = cells[line[1]]
        val c = cells[line[2]]
        if (a != null && a == b && b == c) return a
    }
    return null
}

fun main() {
    val scanner = Scanner(System.`in`)
    val cells: Array<Player?> = Array(9) { null }

    printMatrix(cells)

    var currentPlayer = Player.X

    println("Current player: X, enter 2 numbers as a position of new X or O")
    println("For example ")

    while (true) {
        if (!scanner.hasNextLine()) return
        val input = scanner.nextLine().trim()
        val parts = input.split(" ").filter { it.isNotEmpty() }

        if (parts.size < 2) {
            println("You should enter numbers!")
            continue
        }

        val a = parts[0]
        val b = parts[1]
        val row: Int
        val col: Int
        try {
            row = a.toInt()
            col = b.toInt()
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
            continue
        }

        if (row !in 1..3 || col !in 1..3) {
            println("Coordinates should be from 1 to 3!")
            continue
        }

        val r = row - 1
        val c = col - 1
        val idx = r * 3 + c

        if (cells[idx] != null) {
            println("This cell is occupied! Choose another one!")
            continue
        }

        cells[idx] = currentPlayer
        printMatrix(cells)

        val winner = hasWinner(cells)
        val emptyCount = cells.count { it == null }

        if (winner != null) {
            println("${winner.symbol} wins")
            return
        }
        if (emptyCount == 0) {
            println("Draw")
            return
        }

        currentPlayer = currentPlayer.other()
    }
}
