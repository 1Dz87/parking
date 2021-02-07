package du.lessons.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    public void smth() {
        /*Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Set<Integer> tree = new TreeSet<>(comparator);*/

        Set<Integer> newSet = new TreeSet<>((o1, o2) -> {
            return o1 - o2;
        });
    }

}
