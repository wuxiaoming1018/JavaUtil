package diy.xiaoming.com.javalib.Stack;

import java.util.Stack;

/**
 * ??????????????????????
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
        System.out.println("��׺���ʽ:" + resultData);
        toPolishDate(resultData);
    }

    /**
     * ?????????????
     * -???????????????????????????��???????????????????-????0
     * -5-(-8-1)????? 0-5-(0-8-1)
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
                    //?????????????????
                    resultStack.push(c);
                    sb.reverse();
                } else {
                    sb.append(c);
                }
            } else if (operator.indexOf(c) != -1) {
                if (sb.length() > 0) {
                    resultStack.push(sb.toString());//���С������λ�����
                    sb = new StringBuffer();
                }
                if (charStack.isEmpty()) {
                    charStack.push(c);
                } else {
                    if (!charStack.isEmpty()) {
                        while (isAdd && priorityCompare(charStack.peek(), c)) {
                            resultStack.push(charStack.pop());//pop()ȡ��ջ��Ԫ�ز�ɾ��
                            isAdd = !charStack.isEmpty();
                        }
                    }
                    if (isComplete) {
                        //һ������С����
                        while (charStack.peek() != '(') {
                            resultStack.push(charStack.pop());
                        }
                        charStack.pop();//?????????????'('
                        isComplete = false;
                    } else {
                        charStack.push(c);
                    }
                    isAdd = !charStack.isEmpty();
                }
            } else {
                System.out.println("??????????????????");
                resultStack.clear();
                charStack.clear();
                return;
            }
        }
        while (!charStack.isEmpty()) {
            //���ε�������ջ����Ԫ��
            resultStack.push(charStack.pop());
        }
        System.out.println("resultStack: " + resultStack);
        System.out.print("???????:");
        for (int i = 0; i < resultStack.size(); i++) {
            System.out.print(resultStack.get(i) + " ");
        }
        System.out.println();
        try {
            double result = calculator(resultStack);
            System.out.println("???????:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????? ????a????????????��??b????????a???????????????????????????
     * ??b?????????????��?????��?????b??????????
     *
     * @param a ????????
     * @param b ????????????
     * @return true :????????????  false ??b??????
     */
    private static boolean priorityCompare(char a, char b) {
        if (hasLeft) {
            if (b != ')') {
                if (count > 1) {
                    //????????????????????????????
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
                            throw new RuntimeException("?????????????");
                        } else {
                            temp = number1 / number2;
                        }
                        break;
                    default:
                        throw new Exception("???????:" + object + "��???");
                }
                data.push(temp);
            } else {
                data.push(Double.parseDouble(object));
            }
        }
        return data.pop();
    }

}
