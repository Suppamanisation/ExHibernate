import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "DEPARTMENT")
@NamedQuery(name="get_total_dept", query="select count(1) from Department")

@NamedQueries({
        @NamedQuery(name="get_dept_name_by_id", query="select name from Department where id=:id"),
        @NamedQuery(name="get_all_dept", query="from Department")
})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DPT_ID")
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                '}';
    }
}
