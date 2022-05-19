package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;
import java.util.*;

public class StudentService implements StudentI {


    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Student> stl = s.createQuery("from Student",Student.class).list();
        s.close();
        return stl;
    }

    @Override
    public void createStudent(Student student) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Student student = s.get(Student.class, email);
            if(student == null)
                throw new HibernateException("Did not find Student");
            else
                return student;

        } catch (HibernateException exception) {
       //     exception.printStackTrace();
            System.out.println("did not find the student.");
        } finally {
            s.close();
        }
//        return new Student();
          return null;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student student = getStudentByEmail(email);
        if (student == null){
            return false;
        }
        return password.equals(student.getPassword());
    }


    @Override
    public void registerStudentToCourse(String email, int courseId) {
        //get the student by email
        Student student = getStudentByEmail(email);
        //get course by courseId
        CourseService cs = new CourseService();
        Course course = cs.getCourseById(courseId);
        //add student to the course
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = s.beginTransaction();
            course.getStudents().add(student);
            student.getCourses().add(course);
            s.save(student);
            s.save(course);
            tx.commit();
        } catch (HibernateException exception){
            exception.printStackTrace();
            if(tx!=null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Student student = getStudentByEmail(email);
        if (student == null){
            return null;
        }
       return student.getCourses();
    }
}
