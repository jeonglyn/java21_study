package com.study.java21.string;

public class StringPractice {
    // String API 신기능
    // Java 11~15에서 추가된 String 신규 메서드
    // 기존의 trim() 이나 String.format()의 한계를 보완, 빈 문자열 판별, 반복, 멀티라인 처리를 더 편하게 만든다.

    public static void main(String[] args) {
            // 1. strip / stripLeading / stripTrailing (JAVA11)
            // trim() 과 달리 Unicode 공백까지 제거함.
            // trim은 ASCII(아스키) 공백만 인식하지만 strip은 유니코드 공백 문자까지 인식함

            String str = "   Hello, World!   ";

            // 기존의 trim() : ASCII 공백만 제거
            System.out.println(str.trim());             // "Hello, World!"

            // * strip() : 문자열의 앞과 뒤에 있는 모든 공백 제거
            System.out.println(str.strip());            // "Hello, World!"

            // * stripLeading() : 문자열의 앞(시작 부분)에 있는 공백만 제거
            System.out.println(str.stripLeading());     // "Hello, World!   "

            // * stripTrailing() : 문자열의 뒤(끝 부분)에 있는 공백만 제거
            System.out.println(str.stripTrailing());    // "   Hello, World!"



            // 2. isBlank (Java11)
            // 문자열이 완전히 비어있거나, 오직 공백(유니코드 공백 포함)으로만 이루어져 있는지 확인하여 boolean 값을 반환
            // 공백, 탭, 줄바꿈만 있으면 true.

            // 기존 isEmpty()와의 결정적 차이
            // - isEmpty() 는 문자열의 길이만 봄. 즉 스페이스(" ")가 하나라도 들어있으면 빈 값이 아니라고 판단(false)함
            // - isBlank() 는 문자열의 내부를 확인하여 글자 없이 공백만 있다면 비어있다고 판단(true)함

            System.out.println("".isEmpty());       // true
            System.out.println(" ".isEmpty());      // false - 길이가 0이 아님
            System.out.println(" ".isBlank());      // true - 길이가 0이 아니어도 공백만 있으므로

            String input = "   ";

            if(input.isBlank()) {
                System.out.println("입력값이 없음");   // 입력값이 없음 출력됨
            }



            // 3. repeat (Java11)
            // 지정한 횟수(count)만큼 원본 문자열을 반복하여 이어 붙인 새로운 문자열을 반환
            String star = "*";
            String result = star.repeat(5);
            System.out.println(result);                 // *****

            String star2 = "Hello";
            String result2 = star2.repeat(3);
            System.out.println(result2);                // HelloHelloHello


            // * repeat 사용시 주의사항
            // - 음수 입력 불가 : 파라미터로 count에 음수(-1)을 넣으면 에러 발생
            // - 0 입력시 : 0을 넣으면 아무것도 반복하지 않으므로 빈 문자열("")을 반환
            String result3 = star2.repeat(0);
            System.out.println(result3);                // 빈문자열 반환


            // 4. lines (Java11)
            // 여러 줄로 이루어진 문자열을 줄바꿈 기호를 기준으로 쪼개어 Stream<String> 형태로 변환
            // Stream이므로 filter, map 등과 바로 사용가능
            String multiLine = "Hello,\nWorld!";

            multiLine.lines().forEach(System.out::println);  // 1) Hello,  2) World!


            // Stream이므로 중간 연산 연결 가능
            long count = multiLine.lines().filter(line -> line.contains("World")).count();
            System.out.println(count);                       // 1


            // 5. formatted (Java15)
            // 문자열 내의 특정 자리에 변수나 데이터를 삽입하여 서식을 지정하는 인스턴스 메서드
            // 기존에 사용하던 String,format()과 동일한 기능이지만, 코드를 훨씬 더 직관적이고, 깔끔하게 작성할 수 있음

            // 기존 String.format()
            String old = String.format("이름: %s, 나이: %d", "Alice", 30);

            // formatted() - 인스턴스 메서드라 더 자연스럽게 연결됨
            // 문자열 서식 템플릿을 먼저 완성도 있게 선언한 직후, 마침표(.)를 찍어 변수를 주입함
            // %s : String, %d : int, %f : float, %b : boolean, %c : char
            String formatted = "이름: %s, 나이: %d".formatted("Alice", 30);

            System.out.println(old);                    // 이름: Alice, 나이: 30
            System.out.println(formatted);              // 이름: Alice, 나이: 30



            // 6. indent (Java12)
            // 문자열의 모든 줄 앞에 일괄적으로 들여쓰기를 추가하거나 제거하는 인스턴스 메서드
            // 여러 줄의 텍스트 블록을 다룰 때 코드의 정렬 상태를 깔끔하게 유지할 수 있도록 해줌

            // indent(int n) : 숫자를 기입하여 들여쓰거나 제거할 수 있음

            // 1) 양수 입력 : 문자열의 모든 줄 맨 앞에 지정한 개수만큼의 일반 공백을 추가
            String text = "Line 1\nLine 2\nLine 3";

            // 모든 줄 앞에 공백 3번씩 추가
            String inserted = text.indent(3);

            System.out.println(inserted);

            /*  출력 결과
                Line 1
                Line 2
                Line 3
            * */

            // 2) 음수 입력 : 문자열의 모든 줄 맨 앞의 공백을 지정한 개수만큼 제거
            String removed = inserted.indent(-3);

            System.out.println(removed);

            /*  출력 결과
            Line 1
            Line 2
            Line 3
            */
    }
}
