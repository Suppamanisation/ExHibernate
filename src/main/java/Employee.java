import javax.persistence.*;


@Entity
@Table(name = "EMPLOYEE")
@NamedNativeQuery(name = "get_total_emp", query = "select count(1) from EMPLOYEE")

@NamedNativeQueries({
        @NamedNativeQuery(name = "get_total_emp_by_dept", query = "select count(1) from EMPLOYEE where dpt_id=:did"),
        @NamedNativeQuery(name = "get_all_emp", query = "select * from EMPLOYEE",resultClass=Employee.class)
})

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AGE", nullable = false)
    private int age;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name= "SALARY")
    private int salary;

    @ManyToOne
    @JoinColumn(name = "DPT_ID")
    private Department department;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", department=" + department.toString() +
                '}';
    }
}