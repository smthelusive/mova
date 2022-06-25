import smthelusive.mova.Compiler
import spock.lang.Specification

class MovaProgramSpec extends Specification {

    def runProgram(String programName, String arguments = "") {
        def program = new File(getClass().getResource("/${programName}.mova").toURI())
        Compiler.main(program.absolutePath)
        def sout = new StringBuilder(), serr = new StringBuilder()
        def proc = "java ${programName} ${arguments}".execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        return sout.toString()
    }

    def "integer expressions"() {
        when:
        def result = runProgram("IntegerExpressions")

        then:
        result.toString() equals(
                "2\n" +
                "36\n" +
                "2\n" +
                "25\n" +
                "1352523\n")
    }

    def "decimal expressions"() {
        when:
        def result = runProgram("DecimalExpressions")

        then:
        result.toString() equals(
                "1.56\n" +
                "0.6675\n" +
                "0.6675\n" +
                "23.6675\n" +
                "0.6675\n" +
                "1\n" +
                "-12.9\n")
    }

    def "increment and decrement"() {
        when:
        def result = runProgram("IncrementDecrement")

        then:
        result.toString() equals(
                "1\n" +
                "0\n" +
                "1\n" +
                "0\n" +
                "1\n" +
                "2\n" +
                "1\n" +
                "0\n" +
                "122\n" +
                "121\n" +
                "123\n" +
                "2.34\n" +
                "3.34\n")
    }

    def "integer conditions"() {
        when:
        def result = runProgram("IntegerConditions")

        then:
        result.toString() equals(
                "correct\n" +
                "correct\n" +
                "correct\n" +
                "it works 1\n" +
                "it works 2\n" +
                "it works 3\n" +
                "it works 4\n" +
                "correct\n" +
                "correct\n" +
                "correct\n" +
                "awesome\n" +
                "yes\n" +
                "yes\n" +
                "right\n" +
                "yess\n")
    }

    def "decimal conditions"() {
        when:
        def result = runProgram("DecimalConditions")

        then:
        result.toString() equals(
                "test\n" +
                "correct\n" +
                "yesss\n" +
                "super awesome\n" +
                "great!\n" +
                "yes :)\n" +
                "correct!\n" +
                "right\n")
    }

    def "string expressions"() {
        when:
        def result = runProgram("StringExpressions")

        then:
        result.toString() equals(
                "this is a test\n" +
                "lalal\\\"a\n" +
                "aanother test\n" +
                "wow, hello world\n" +
                "worldhello \n" +
                "11\n" +
                "fdsgasd1.55\n" +
                "-6456.32\n" +
                "hello eeerrr\n" +
                "eeehello rrr\n")
    }

    def "string conditions"() {
        when:
        def result = runProgram("StringConditions")

        then:
        result.toString() equals(
                "yes\n" +
                "correct\n" +
                "correct\n" +
                "correct\n" +
                "superNice\n" +
                "correct\n" +
                "that's right!\n")
    }

    def "loops"() {
        when:
        def result = runProgram("Loops")

        then:
        result.toString() equals(
                "1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "five times\n" +
                "five times\n" +
                "five times\n" +
                "five times\n" +
                "five times\n" +
                "two\n" +
                "two\n" +
                "three\n" +
                "three\n" +
                "three\n")
    }

    def "program arguments"() {
        when:
        def result = runProgram("ProgramArguments", "15 2,8")

        then:
        result.toString() equals(
                "15 2,8\n" +
                "17.8\n" +
                "159.2\n" +
                "12.0\n" +
                "159.2\n" +
                "12.0\n" +
                "12.0\n" +
                "9.22,8\n" +
                "9.22,8\n" +
                "13.0\n" +
                "14.0\n" +
                "15.0\n" +
                "16.0\n" +
                "17.0\n" +
                "11.8\n" +
                "11.8\n" +
                "11.8\n" +
                "correct\n")
    }

    def "reverse string"() {
        when:
        def result = runProgram("ReverseString")

        then:
        result.toString() equals(
                "tset\n" +
                "test\n" +
                "20.1\n")
    }
}