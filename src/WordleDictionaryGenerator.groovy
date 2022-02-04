int size = 0
StringBuilder sb = new StringBuilder()
new File("/usr/share/dict/words").withReader { br ->
    String line
    while ((line = br.readLine()) != null) {
        if (line =~ /^[a-z]{5}$/) {
            size++
            println "${line}"
            sb.append(line).append("\n")
        }
    }
}

new File("wordle_words.txt").text = sb

println size