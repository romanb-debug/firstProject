import java.util.Scanner

fun printMatrix(cells: CharArray) {
    println("---------")
    for (r in 0..2) {
        print("| ")
        for (c in 0..2) {
            val ch = cells[r * 3 + c]
            print(ch)
            if (c < 2) print(' ')
        }
        println(" |")
    }
    println("---------")
}

fun hasWinner(cells: CharArray, player: Char): Boolean {
    val lines = arrayOf(
        intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
        intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
        intArrayOf(0,4,8), intArrayOf(2,4,6)
    )
    for (line in lines) {
        if (cells[line[0]] == player && cells[line[1]] == player && cells[line[2]] == player) return true
    }
    return false
}

fun main() {
    val scanner = Scanner(System.`in`)
    val cells = CharArray(9) { ' ' }

    printMatrix(cells)

    var currentPlayer = 'X'

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

        if (cells[idx] != ' ') {
            println("This cell is occupied! Choose another one!")
            continue
        }

        cells[idx] = currentPlayer
        printMatrix(cells)

        val xWins = hasWinner(cells, 'X')
        val oWins = hasWinner(cells, 'O')
        val emptyCount = cells.count { it == ' ' }

        if (xWins) {
            println("X wins")
            return
        }
        if (oWins) {
            println("O wins")
            return
        }
        if (emptyCount == 0) {
            println("Draw")
            return
        }

        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }
}
