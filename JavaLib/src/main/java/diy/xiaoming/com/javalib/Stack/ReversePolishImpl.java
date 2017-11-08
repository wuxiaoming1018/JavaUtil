package diy.xiaoming.com.javalib.Stack;

import java.util.Stack;

/**
 * ��ͨ��������Ȳ������ʽʵ��
 * Created by Administrator on 2017-11-07.
 */

public class ReversePolishImpl {

    private String CLOSE_MARK = "bye";
    private boolean hasLeft = false;
    private Stack<Character> charStack = new Stack<>();
    private Stack<Object> resultStack = new Stack<>();
    private boolean isComplete;//�Ƿ���һ������������
    private String operator = "+-*/()";
    private int count = 1;

    public void init() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("������������ʽ");
//        String input = scanner.nextLine();
//        while (input.length() != 0 && !CLOSE_MARK.equals(input)) {
//            System.out.println("�������ʽ:");
//            toPolishDate(input);
//            System.out.println("�沨�����ʽ:");
//            roReversePolish(input);
//        }
//        toPolishDate("3+5*2-(8-3)*2/4");
//        toPolishDate("8-2*3+(8-2)*5/6");
        String resultData = beforeData("-3+(-12-5+10*2-8)*6/3");
//        String resultData = beforeData("1+((2+3)*4)-5");
//        String resultData = beforeData("8-2*3+(89-2)*5/6");
        System.out.println("��׺���ʽ:" + resultData);
        toPolishDate(resultData);
    }

    /**
     * ������ָ������
     * -��������ǰ����߳����������ź��棬���ʾ������Ϊ��������-ǰ���0
     * -5-(-8-1)�ᴦ��� 0-5-(0-8-1)
     *
     * @param before
     * @return
     */
    private String beforeData(String before) {
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

    private void toPolishDate(String input) {
        int length = input.length();
        char c;
        hasLeft = input.charAt(0) == '(';
        boolean isAdd = true;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                if (i == length - 1) {
                    //�����Ϊ�ַ�λ���ֵ����
                    resultStack.push(c);
                    sb.reverse();
                } else {
                    sb.append(c);
                }
            } else if (operator.indexOf(c) != -1) {
                if (sb.length() > 0) {
                    resultStack.push(sb.toString());//����������Ͷ�λ������
                    sb = new StringBuffer();
                }
                if (charStack.isEmpty()) {
                    charStack.push(c);
                } else {
                    if (!charStack.isEmpty()) {
                        while (isAdd && priorityCompare(charStack.peek(), c)) {
                            resultStack.push(charStack.pop());//pop()��ȡջ��Ԫ�ز�ɾ��
                            isAdd = !charStack.isEmpty();
                        }
                    }
                    if (isComplete) {
                        //����һ������������
                        while (/*isAdd &&*/ charStack.peek() != '(') {
                            resultStack.push(charStack.pop());
//                            isAdd = !charStack.isEmpty();
                        }
                        charStack.pop();//ȥ������ջ�����'('
                        isComplete = false;
                    } else {
                        charStack.push(c);
                    }
                    isAdd = !charStack.isEmpty();
                }
            } else {
                System.out.println("������ַ�������Ҫ��");
                resultStack.clear();
                charStack.clear();
                return;
            }
        }
        System.out.println("charStack: " + charStack);
        while (!charStack.isEmpty()) {
            //���ѷ���ջ�����Ԫ�����ε���
            resultStack.push(charStack.pop());
        }
        System.out.println("resultStack: " + resultStack);
        System.out.print("��׺���ʽ:");
        for (int i = 0; i < resultStack.size(); i++) {
            System.out.print(resultStack.get(i) + " ");
        }
    }

    /**
     * ���ȼ���Ա� ����a��������ȼ���С��b�����ʱ��a������������µ�ջ������������
     * ��b��������ȼ����бȽϣ���С��ʱ��b�������ջ��
     *
     * @param a ջ�������
     * @param b ��Ҫ�Աȵ������
     * @return true :ջ�����������  false ��b�����ջ
     */
    private boolean priorityCompare(char a, char b) {
        if (hasLeft) {
            if (b != ')') {
                if (count > 1) {
                    //��������������������һ����ʱ��
                    hasLeft = false;
                    return priorityCompare(a,b);
//                    return true;
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

            if (a == '(') {//ջ��Ϊ�����ţ�bԪ��ֱ����ջ
                hasLeft = true;
                return false;
            }
        }
        return false;
    }

}
