package calculator;

/**
 * The interface for a calculator.
 * This calculator has two methods: input() and getResult().
 * It takes in one char and a time and calculate the result when getResult() is called.
 * It allows + - * operations.
 */
public interface Calculator {
  /**
   * Enter an input to the calculator.
   *
   * @param c a single char as the input.
   * @return a new calculator which accepts the input.
   */
  Calculator input(char c);

  /**
   * Get the calculation result of the current calculator.
   *
   * @return a String representation of the result.
   */
  String getResult();
}
