package diy.xiaoming.com.javalib.Stack;

import java.util.Stack;

/**
 * 逆波兰表达式实现
 * Created by Administrator on 2017-11-07.
 */

public class ReversePolishImpl {

    private static boolean hasLeft = false;
    private static Stack<Character> charStack = new Stack<>();
    private static Stack<Object> resultStack = new Stack<>();
    private static boolean isComplete;//??????????????????
    private static String operator = "+-*/()";
    private static int count = 1;

    public static void main(String[] args) {
        String resultData = beforeData("8-(4+3-6+2.5)*4");
        System.out.println("中缀表达式:" + resultData);
        toPolishDate(resultData);
    }

    /**
     * 数据处理
     * -号在最左边或者在(的右边则当成是负号，需要在前面加0
     * -5-(-8-1)处理为： 0-5-(0-8-1)
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
                    //把最后一位数字入栈
                    resultStack.push(c);
                    sb.reverse();
                } else {
                    sb.append(c);
                }
            } else if (operator.indexOf(c) != -1) {
                if (sb.length() > 0) {
                    resultStack.push(sb.toString());//解决小数，多位数情况
                    sb = new StringBuffer();
                }
                if (charStack.isEmpty()) {
                    charStack.push(c);
                } else {
                    if (!charStack.isEmpty()) {
                        while (isAdd && priorityCompare(charStack.peek(), c)) {
                            resultStack.push(charStack.pop());//pop()取出栈顶元素并删除
                            isAdd = !charStack.isEmpty();
                        }
                    }
                    if (isComplete) {
                        //一个完整小括号
                        while (charStack.peek() != '(') {
                            resultStack.push(charStack.pop());
                        }
                        charStack.pop();//去除左括号'('
                        isComplete = false;
                    } else {
                        charStack.push(c);
                    }
                    isAdd = !charStack.isEmpty();
                }
            } else {
                System.out.println("您输入的计算公式有问题，请重新输入");
                resultStack.clear();
                charStack.clear();
                return;
            }
        }
        while (!charStack.isEmpty()) {
            //依次弹出符号栈里面元素
            resultStack.push(charStack.pop());
        }
        System.out.println("resultStack: " + resultStack);
        System.out.print("后缀(逆波兰)表达式:");
        for (int i = 0; i < resultStack.size(); i++) {
            System.out.print(resultStack.get(i) + " ");
        }
        System.out.println();
        try {
            double result = calculator(resultStack);
            System.out.println("计算结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当符号栈栈顶符号的优先级别不小于需要入栈的运算符优先级别，则符号栈顶元素弹出，运算符入栈，否则运算符不入符号栈
     *
     *
     * @param a 符号栈顶元素
     * @param b 需要判断是否入栈的运算符
     * @return true :a弹出  false ??b??????
     */
    private static boolean priorityCompare(char a, char b) {
        if (hasLeft) {
            if (b != ')') {
                if (count > 1) {
                    //括号里面的运算符号个数大于1的时候
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

            if (a == '(') {//???????????b?????????
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
                            throw new RuntimeException("被除数不能为0");
                        } else {
                            temp = number1 / number2;
                        }
                        break;
                    default:
                        throw new Exception("您输入的运算符号:" + object + "未知");
                }
                data.push(temp);
            } else {
                data.push(Double.parseDouble(object));
            }
        }
        return data.pop();
    }

}
