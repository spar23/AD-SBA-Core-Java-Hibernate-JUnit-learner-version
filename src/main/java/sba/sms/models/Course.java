package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
//import org.hibernate.mapping.List;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(length = 50)
    int id;
    @NonNull
    @Column(length = 50, nullable = false)
    String name;
    @NonNull
    @Column(length = 50,nullable = false)
    String instructor;

    public Course(int id, @NonNull String name, @NonNull String instructor) {
        this.instructor = instructor;
        this.id = id;
        this.name = name;
    }

//    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    List<Student> students = new java.util.ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Course other = (Course) obj;
        if (this.id != other.id) {
            return false;
        }

        if (!this.name.equals(other.name)) {
            return false;
        }
        if (!this.instructor.equals(other.instructor)) {
            return false;
        }

        return true;
    }

}
