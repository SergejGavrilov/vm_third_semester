package hw_2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Lambdas {
    private static final Map<Character, BinaryOperator<Integer>> operatorMap =
            new HashMap<Character, BinaryOperator<Integer>>() {{
                put('+', (a, b) -> a + b);
                put('-', (a, b) -> a - b);
                put('*', (a, b) -> a * b);
            }};

    public static void main(String[] args) {
        Node<Integer, Character> tree = new BiNode<>(
                '+',
                new BiNode<>(
                        '*',
                        new Leaf<>(2),
                        new Leaf<>(3)
                ),
                new BiNode<>(
                        '-',
                        new Leaf<>(-100),
                        new Leaf<>(33)
                )
        );
        System.out.println("evaluate");
        System.out.println(evaluate(tree));
        System.out.println("to string");
        System.out.println(toString(tree));
        System.out.println("invert sign");
        System.out.println(toString(invertSign(tree, (e) -> -e)));
    }

    public static Integer evaluate(Node<Integer, Character> tree) {
        return evaluateHard(tree, operatorMap);
    }

    public static <Num, Operation> Num evaluateHard(Node<Num, Operation> tree, Map<Operation, BinaryOperator<Num>> operatorMap) {
        return tree.process(
                //for leafs return value
                (e) -> e,
                //for binodes apply operation
                (arg1, arg2, arg3) -> operatorMap.get(arg1).apply(arg2, arg3));
    }

    public static <Num, Operation> String toString(Node<Num, Operation> tree) {
        return tree.process(
                //for leafs convert to string
                Num::toString,
                //for binodes sum all strings
                ((arg1, arg2, arg3) -> "(" + arg2 + " " + arg1 + " " + arg3 + ")"));
    }

    public static <Num, Operation> Node<Num, Operation> invertSign(Node<Num, Operation> tree, UnaryOperator<Num> negate) {
        return tree.process(
                //for leafs create new Leaf with negated value
                (e) -> new Leaf<>(negate.apply(e)),
                //for binodes create new BiNode from arguments
                (TreeFunction<Operation, Node<Num, Operation>>) BiNode::new);
    }
}
