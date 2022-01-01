package com.example.demo;

import com.example.demo.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.*;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.groupingBy;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@Slf4j
@Mapper(componentModel = "spring")
public class DemoApplication {

    public static void main(String[] args) throws InterruptedException {
//        test();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(DemoApplication.class, args);

//        migratoryBirds(Arrays.asList(1,3,4,5,6,4,4,4,4,2,2,2,2,2,1,1,1,1,1));
//        User a = new User();
//        a.setUsername("phuong");
//        a.setImage("a1");
//        a.setCreatedDate(ZonedDateTime.now());
//
//        User b = new User();
//        b.setUsername("tung");
//        b.setImage("a2");
//        a.setCreatedDate(ZonedDateTime.now());
//
//        User c = new User();
//        c.setUsername("huy");
//        c.setImage("a3");
//        a.setCreatedDate(ZonedDateTime.now());
//       List<User> users = List.of(a,b,c);
//
//        Map<LocalDate, List<User>> groupByDate = users.parallelStream().
//                collect(
//                        groupingBy(
//                                it -> it.getCreatedDate().toLocalDate()
//                                , HashMap::new,
//                                Collectors.toList()));


//        long currentTime=System.currentTimeMillis();
//        List<Integer> data=new ArrayList<Integer>();
//        for (int i = 0; i < 10000; i++) {
//            data.add(i);
//        }
//
//        long sum=data.stream()
//                .parallel()
//                .map(i ->(int)Math.sqrt(i))
//                .map(DemoApplication::performComputation)
//                .reduce(0,Integer::sum);
//
//        System.out.println(sum);
//        long endTime=System.currentTimeMillis();
//        System.out.println("Time taken to complete:"+(endTime-currentTime));
//        Stream<String> streamOfArray = Stream.of("a", "b", "c");
//        Stream<String> streamBuilder =
//                Stream.<String>builder().add("a").add("b").add("c").build();
//        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
//        streamIterated.forEach(System.out::println);
//        Stream.iterate(1, n -> n < 20 , n -> n * 2)
//                .forEach(System.out::println);

//        List<String> list = Arrays.asList("abc1", "abc2", "abc3" ,"abc4");
//        long size = list.stream().skip(1)
//                .map(element -> element.substring(0, 3)).sorted().count();
//        int reducedParams = Arrays.asList(1, 2,3).parallelStream()
//                .reduce(10, (a, b) -> a + b, (a, b) -> {
//                    log.info("combiner was called");
//                    return a + b;
//                });
//        System.out.println(reducedParams);
    }

    public static int performComputation(int number)
    {
        int sum=0;
        for (int i = 1; i < 1000000; i++) {
            int div=(number/i);
            sum+=div;

        }
        return sum;

    }
 public static void test() {
//     int[] a = {1, 2, 3, 4, 5};
//     Function<Integer,Integer> getInt = i->i;
//
//     List<Integer> lst = Arrays.stream(a).boxed().collect(Collectors.toList());
//     int z = lst.indexOf(0)
//     lst.sort(Comparator.naturalOrder());
//     lst.forEach(System.out::print);
//     lst.sort(Comparator.reverseOrder(),
//             (i, j) -> {
//                 if (i.equals(j)) return 0;
//                 else if (i < j) return 1;
//                 else return -1;
//             }));
//     lst.forEach(System.out::print);
//     User user = User.builder().username("haha").id(1L).email("ok").build();
//     Function<User,String> getUser = User::getUsername;
//
//     Function<String, Permission> getPermission = (str)->{
//         if(str.equals("haha1")) return new Permission();
//         else return null;
//     };
//     Function<User, Permission> ok =getUser.andThen(getPermission);
//     System.out.println(ok.apply(user));
     String word = "CAT";
     String text = "CATAaC";
     Boolean found;

     found = text.toUpperCase().contains(word);
     System.out.println(found);
 }

    public static void migratoryBirds(List<Integer> arr) {
        // n^2
        Map<Object, Long> map = arr.stream().collect(groupingBy(e -> e, Collectors.counting()));
        Map.Entry<Object, Long> maxEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get();


        System.out.println(maxEntry.getKey());

        System.out.println(map.getClass());


    }


}
