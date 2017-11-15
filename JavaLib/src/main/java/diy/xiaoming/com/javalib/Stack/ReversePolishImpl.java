package diy.xiaoming.com.javalib.Stack;

import java.util.Stack;

/**
 * 普通四则运算比波兰表达式实现
 * Created by Administrator on 2017-11-07.
 */

public class ReversePolishImpl {

    private static boolean hasLeft = false;
    private static Stack<Character> charStack = new Stack<>();
    private static Stack<Object> resultStack = new Stack<>();
    private static boolean isComplete;//是否是一个完整的括号
    private static String operator = "+-*/()";
    private static int count = 1;

    public static void main(String[] args) {
        String resultData = beforeData("8-(4+3-6+2.5)*4");
        System.out.println("中缀表达式:" + resultData);
        toPolishDate(resultData);
    }

    /**
     * 解决出现负数情况
     * -出现在最前面或者出现在左括号后面，则表示该数字为负数，在-前面加0
     * -5-(-8-1)会处理成 0-5-(0-8-1)
     *
     * @param before
     * @return
     */
    private static String beforeData(String before) {
        StringBuffer sb = new StringBuffer(before);
        char be, af;
        if (sb.charAt(0) == '-') {
            sb.insert(0, '0');
        }
        for (int i = 0; i < sb.length() - 1; i++) {
            be = sb.charAt(i);
            af = sb.charAt(i + 1);
            if (be == '(' && af == '-') {
                sb.insert(i + 1, '0');
            }
        }
        return sb.toString();
    }

    private static void toPolishDate(String input) {
        int length = input.length();
        char c;
        hasLeft = input.charAt(0) == '(';
        boolean isAdd = true;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                if (i == length - 1) {
                    //最后以字符为数字的情况
                    resultStack.push(c);
                    sb.reverse();
                } else {
                    sb.append(c);
                }
            } else if (operator.indexOf(c) != -1) {
                if (sb.length() > 0) {
                    resultStack.push(sb.toString());//解决非整数和多位数问题
                    sb = new StringBuffer();
                }
                if (charStack.isEmpty()) {
                    charStack.push(c);
                } else {
                    if (!charStack.isEmpty()) {
                        while (isAdd && priorityCompare(charStack.peek(), c)) {
                            resultStack.push(charStack.pop());//pop()获取栈顶元素并删除
                            isAdd = !charStack.isEmpty();
                        }
                    }
                    if (isComplete) {
                        //出现一个完整的括号
                        while (charStack.peek() != '(') {
                            resultStack.push(charStack.pop());
                        }
                        charStack.pop();//去掉符号栈里面的'('
                        isComplete = false;
                    } else {
                        charStack.push(c);
                    }
                    isAdd = !charStack.isEmpty();
                }
            } else {
                System.out.println("输入的计算公式不符合要求");
                resultStack.clear();
                charStack.clear();
                return;
            }
        }
        while (!charStack.isEmpty()) {
            //最后把符号栈里面的元素依次弹出,并加到结果栈里面
            resultStack.push(charStack.pop());
        }
        System.out.println("resultStack: " + resultStack);
        System.out.print("后缀表达式:");
        for (int i = 0; i < resultStack.size(); i++) {
            System.out.print(resultStack.get(i) + " ");
        }
        System.out.println();
        try {
            double result = calculator(resultStack);
            System.out.println("运算结果为:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 优先级别对比 （当a运算符优先级别不小于b运算符时，a运算符弹出，新的栈顶运算符会继续
     * 和b运算符优先级进行比较，当小于时候，b运算符入栈）
     *
     * @param a 栈顶运算符
     * @param b 需要对比的运算符
     * @return true :栈顶运算符弹出  false ：b入符号栈
     */
    private static boolean priorityCompare(char a, char b) {
        if (hasLeft) {
            if (b != ')') {
                if (count > 1) {
                    //当括号里面的运算符大于一个的时候
                    hasLeft = false;
                    return priorityCompare(a, b);
                } else {
                    count++;
                    return false;
                }
            } else {
                count = 1;
                isComplete = true;
                hasLeft = false;
                return false;
            }
        } else {
            if (b == '(') {
                hasLeft = true;
                return false;
            }
            if (a == b) {
                return true;
            }
            if (a == '+' || a == '-') {
                if (b == '-' || b == '+') {
                    return true;
                } else {
                    return false;
                }
            }
            if (a == '*' || a == '/') {
                return true;
            }

            if (a == '(') {//栈顶为左括号，b元素直接入栈
                hasLeft = true;
                return false;
            }
        }
        return false;
    }

    private static double calculator(Stack<Object> resultStack) throws Exception {
        Stack<Double> data = new Stack<>();
        Double number1, number2, temp;
        for (int i = 0; i < resultStack.size(); i++) {
            String object = String.valueOf(resultStack.get(i));
            if (operator.indexOf(object) >= 0) {
                number2 = data.pop();
                number1 = data.pop();
                switch (object) {
                    case "+":
                        temp = number1 + number2;
                        break;
                    case "-":
                        temp = number1 - number2;
                        break;
                    case "*":
                        temp = number1 * number2;
                        break;
                    case "/":
                        if (number2 == 0) {
                            throw new RuntimeException("被除数不能为零");
                        } else {
                            temp = number1 / number2;
                        }
                        break;
                    default:
                        throw new Exception("运算符号:" + object + "未识别");
                }
                data.push(temp);
            } else {
                data.push(Double.parseDouble(object));
            }
        }
        return data.pop();
    }

}
