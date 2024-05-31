package calculator;

/**
 * A smart calculator for whole number calculations and + - * operations.
 * It allows missing operands or multiple operators such as 32+= or 32+-24.
 */
public class SmartCalculator extends AbstractCalculator implements Calculator {

  /**
   * The only constructor accessible to the user.
   */
  public SmartCalculator() {
    this("", null, null, null);
  }

  /**
   * The default constructor.
   *
   * @param result  The result to be shown by getResult().
   * @param tmp  A temporary placeholder for operands waiting to be operated.
   * @param mode  Operation modes: +, -, or *.
   * @param operand  A number to be operated.
   */
  private SmartCalculator(String result, Integer tmp, Character mode, Integer operand) {
    super(result, tmp, mode, operand);
  }

  @Override
  protected SmartCalculator controller(char in) throws IllegalArgumentException {
    // input is a digit.
    if (48 <= in && in <= 57) {
      return new SmartCalculator(result + in, tmp, mode, addDigit(in - 48));
    }
    // input is an operator.
    switch (in) {
      case '+':
      case '-':
      case '*':
        if (operand == null) {
          // case: 32+- -> interpretation: 32 - ?
          if (mode != null) {
            return new SmartCalculator(result + in, tmp, in, null);
            // case: +32 -> interpretation: 32 ? ?
          } else if (in == '+') {
            return new SmartCalculator(
                    result + in, null, null, null);
          } else {
            // case: -32 -> error
            throw new IllegalArgumentException("Illegal Start of expression");
          }
        }

        // e.g. 32+16+ -> 48+:
        // In this case, saves 48 as a result
        // saves 48 in temporary storage for later calculations and changes the calculation mode
        return new SmartCalculator(
                Integer.toString(operate()) + in,
                operate(),
                in,
                null);
      case '=':
        if (operand == null) {
          // case: 32+= -> 64= (64)
          if (mode != null && tmp != null) {
            int result = new SmartCalculator("", tmp, mode, tmp).operate();
            return new SmartCalculator(
                    Integer.toString(result), result, mode, tmp);
          }
          // case: = -> error
          throw new IllegalArgumentException("No number before equal sign");
        }
        // case: 32+24== -> result: 32+24= tmp: 32 mode: + operand: 24
        // -> 32 + 24 + 24 = 80
        if (result.endsWith("=")) {
          int result = new SmartCalculator("", operate(), mode, operand).operate();
          return new SmartCalculator(
                  Integer.toString(result), result, mode, operand);
        // case: 32+24= -> result: 32+24 tmp: 32 mode: + operand: 24
        // -> 32 + 24 = 56
        } else {
          return new SmartCalculator(
                  Integer.toString(operate()) + '=', tmp, mode, operand);
        }
      // input is the clear command.
      case 'C':
        return new SmartCalculator();
      // other invalid inputs.
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }
}
