package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        ///Создайте коллекцию студентов, где каждый студент содержит информацию о предметах, которые он изучает, и его оценках по этим предметам.
        /// Используйте Parallel Stream для обработки данных и создания Map, где ключ - предмет, а значение - средняя оценка по всем студентам.
        /// Выведите результат: общую Map с средними оценками по всем предметам.
        System.out.println(students.stream().parallel()
                .flatMap(student ->  student.getGrades().entrySet().stream())
                .collect(Collectors.groupingByConcurrent(Map.Entry::getKey, Collectors.averagingInt(Map.Entry::getValue))));
        }
    }