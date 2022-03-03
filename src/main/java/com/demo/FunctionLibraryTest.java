package com.demo;

import java.util.*;

public class FunctionLibraryTest {
    public static void main(String[] args) {
        /*
        FuncA: [String, Integer, Integer]; isVariadic = false
        FuncB: [String, Integer]; isVariadic = true
        FuncC: [Integer]; isVariadic = true
        FuncD: [Integer, Integer]; isVariadic = true
        FuncE: [Integer, Integer, Integer]; isVariadic = false
        FuncF: [String]; isVariadic = false
        FuncG: [Integer]; isVariadic = false
        */
        List<String> f1 = new ArrayList<>();
        List<String> f2 = new ArrayList<>();
        List<String> f3 = new ArrayList<>();
        List<String> f4 = new ArrayList<>();
        List<String> f5 = new ArrayList<>();
        List<String> f6 = new ArrayList<>();
        List<String> f7 = new ArrayList<>();

        f1.add("String"); f1.add("Integer"); f1.add("Integer");
        f2.add("String"); f2.add("Integer");
        f3.add("Integer");
        f4.add("Integer"); f4.add("Integer");
        f5.add("Integer"); f5.add("Integer"); f5.add("Integer");
        f6.add("String");
        f7.add("Integer");
        Function fA = new Function("FuncA", f1, false);
        Function fB = new Function("FuncB", f2, true);
        Function fC = new Function("FuncC", f3, true);
        Function fD = new Function("FuncD", f4, true);
        Function fE = new Function("FuncE", f5, false);
        Function fF = new Function("FuncF", f6, false);
        Function fG = new Function("FuncG", f7, false);
        Set<Function> functionSet = new HashSet<>();
        functionSet.add(fA);functionSet.add(fB);functionSet.add(fC);functionSet.add(fD);functionSet.add(fE);functionSet.add(fF);functionSet.add(fG);
        FunctionLibrary lib = new FunctionLibrary();
        lib.register(functionSet);

        //queries:
        /*
        findMatches({String}) -> [FuncF]
        findMatches({Integer}) -> [FuncC, FuncG]
        findMatches({Integer, Integer, Integer, Integer}) -> [FuncC, FuncD]
        findMatches({Integer, Integer, Integer}) -> [FuncC, FuncD, FuncE]
        findMatches({String, Integer, Integer, Integer}) -> [FuncB]
        findMatches({String, Integer, Integer}) -> [FuncA, FuncB]
         */
        //lib.findMatches(f6);
        //lib.findMatches(f7);
        //lib.findMatches(f5);
        //lib.findMatches(f1);
        f5.add("Integer");
        lib.findMatches(f5);
    }

    static class FunctionLibrary {
        Map<String, List<Function>> nonVariadic = new HashMap<>();
        Map<String, List<Function>> variadic = new HashMap<>();

        public void register(Set<Function> functionSet) {
            for (Function f : functionSet) {
                String key = appendArgs(f.argumentTypes, f.argumentTypes.size());
                if (f.isVariadic) {
                    variadic.putIfAbsent(key, new LinkedList<Function>());
                    variadic.get(key).add(f);
                } else {
                    nonVariadic.putIfAbsent(key, new LinkedList<Function>());
                    nonVariadic.get(key).add(f);
                }
            }
        }

        public List<Function> findMatches(List<String> argumentTypes) {
            List<Function> matches = new ArrayList<>();
            String key = appendArgs(argumentTypes, argumentTypes.size());

            if (nonVariadic.containsKey(key)) {
                matches.addAll(new LinkedList<Function>(nonVariadic.get(key)));
            }
            if (variadic.containsKey(key)) {
                matches.addAll(new LinkedList<Function>(variadic.get(key)));
            }

            int count = argumentTypes.size();
            for (int i = argumentTypes.size() - 2; i >= 0; --i) {
                if (argumentTypes.get(i).equals(argumentTypes.get(i + 1))) {
                    --count;
                } else {
                    break;
                }
                key = appendArgs(argumentTypes, count);
                if (variadic.containsKey(key)) {
                    matches.addAll(new LinkedList<Function>(variadic.get(key)));
                }
            }

            printFunction(matches);
            return matches;
        }

        public void printFunction(List<Function> matches){
            for (Function f: matches) {
                System.out.print(f.name + " ");
            }
            System.out.println();
        }
        public String appendArgs(List<String> argumentTypes, int n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; ++i) {
                String arg = argumentTypes.get(i);
                sb.append(arg);
                sb.append("+");
            }
            return sb.toString();
        }
    }

    static class Function {
        String name;
        List<String> argumentTypes;
        boolean isVariadic;

        Function(String name, List<String> argumentTypes, boolean isVariadic) {
            this.name = name;
            this.argumentTypes = argumentTypes;
            this.isVariadic = isVariadic;
        }
    }
}
