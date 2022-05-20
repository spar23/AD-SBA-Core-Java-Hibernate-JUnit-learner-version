package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Student {

    @Id
  //  @GeneratedValue(strategy = GenerationType.TABLE)
//    @Column(length = 50)
    String email;
    @NonNull
    @Column(length = 50, nullable = false)
    String name;
    @NonNull
    @Column(length = 50, nullable = false)
    String password;

    public Student(String email, @NonNull String name, @NonNull String password) {
        this.password = password;
        this.email = email;
        this.name = name;
    }


//    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    List<Course> courses = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Student other = (Student) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }

        if (!this.email.equals(other.email)) {
            return false;
        }
        if (!this.password.equals(other.password)) {
            return false;
        }

        return true;
    }


}

