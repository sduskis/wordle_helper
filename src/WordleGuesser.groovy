def notFound = /.*/ // Replace with "/.*[some letters].*/" once you find disallowed letters

// Replace with something like /^...a.e$/ if a is required in the third position
// and e is required in the last position.
def green = /^.....$/

// Replace with something like ["a": [1], "t": [0,3], "e": [1]] if a, t, e are
// required but not in those positions.  Note, these numbers are 0 based.
// In this example, "a" cannot be in position 2 (i.e. /.a..../), and "t"
// cannot be in position 1 or 4 (i.e. /t..../ nor /...t./)
def yellow = [:]

def words = new File("wordle_words.txt").readLines()

if (notFound == ".*" && yellow.isEmpty()) {
    // generates the initial guess... or just use "arise"

    // flnd all words where each letter is unique
    words = words.findAll { word -> new HashSet<>(word.toList()).size() == word.length() }

    // The first guess should have 3 unique vowels.
    words = words.findAll(word -> word.toList().findAll { c -> "aeiou".contains(c) }.size() == 3)

    println "Word Count: ${words.size()}"
    def randomWordId = new java.util.Random().nextInt(words.size());
    println "Random Word: ${words[randomWordId]}"
} else {
    def matchesYellow = {
        word ->
            yellow.entrySet().every(entry ->
                    // the word contains the yellow letter
                    word.contains(entry.key)
                            // just not in the positions it was previously found
                            && !entry.value.any { id -> word[id] == entry.key })
    }

    // find all words that:
    //
    // 1) don't have any letters in the "not found" list
    // 2) match the exact positions of green letters
    // 3) have yellow letters, just not in the position they were previously found

    words = words.findAll { word -> !(word ==~ notFound) && (word ==~ green) && matchesYellow(word) }
    words.forEach(System.out::println)
    println "Matching word count: ${words.size()}"
}
