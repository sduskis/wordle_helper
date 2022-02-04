StringBuilder sb = new StringBuilder()
new File("/usr/share/dict/words").eachLine { line ->
    if (line ==~ /^[a-z]{5}$/) {
        sb.append(line).append("\n")
    }
}

new File("wordle_words.txt").text = sb
