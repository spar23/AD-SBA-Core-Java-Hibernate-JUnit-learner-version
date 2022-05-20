package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class CourseServiceTest {

    static CourseService courseService;

    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }

    @Test
    void getAllCourses() {
        String instructorPhillip = "Phillip Witkin";
        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course(1,"Java", instructorPhillip),
        new Course(2,"Frontend", "Kasper Kain"),
        new Course(3,"JPA", "Jafer Alhaboubi"),
        new Course(4,"Spring Framework", instructorPhillip),
        new Course(5,"SQL", instructorPhillip)
        ));

        assertThat(courseService.getAllCourses()).hasSameElementsAs(expected);

    }

}
