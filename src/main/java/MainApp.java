import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class MainApp {
    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Department department = new Department();
            department.setName("IT Department");

            Employee employee1 = new Employee();
            employee1.setName("Adam");
            employee1.setDesignation("Manager");
            employee1.setDepartment(department);
            employee1.setAge(23);
            employee1.setSalary(1000);

            Employee employee2 = new Employee();
            employee2.setName("Miller");
            employee2.setDesignation("Software Engineer");
            employee2.setDepartment(department);
            employee2.setAge(25);
            employee2.setSalary(1500);

            Employee employee3 = new Employee();
            employee3.setName("Smith");
            employee3.setDesignation("Associate  Engineer");
            employee3.setDepartment(department);
            employee3.setAge(30);
            employee3.setSalary(1200);

            Employee employee4 = new Employee();
            employee4.setName("Frank");
            employee4.setDesignation("Q&A Lead");
            employee4.setDepartment(department);
            employee4.setAge(35);
            employee4.setSalary(800);

            department.getEmployees().add(employee1);
            department.getEmployees().add(employee2);
            department.getEmployees().add(employee3);
            department.getEmployees().add(employee4);

            session.persist(department);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            query.select(root);
            Query<Employee> q = session.createQuery(query);
            List<Employee> employees = q.getResultList();
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<Employee> root = query.from(Employee.class);
            query.multiselect(root.get("name"), root.get("designation"));
            Query<Object[]> q = session.createQuery(query);
            List<Object[]> list = q.getResultList();
            for (Object[] objects : list) {
                System.out.println("Name : " + objects[0]);
                System.out.println("Designation : " + objects[1]);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(builder.count(root));
            Query<Long> query = session.createQuery(criteriaQuery);
            long count = query.getSingleResult();
            System.out.println("Count = " + count);

            CriteriaQuery<Integer> criteriaQuery2 = builder.createQuery(Integer.class);
            Root<Employee> root2 = criteriaQuery2.from(Employee.class);
            criteriaQuery2.select(builder.max(root2.get("salary")));
            Query<Integer> query2 = session.createQuery(criteriaQuery2);
            int maxSalary = query2.getSingleResult();
            System.out.println("Max Salary = " + maxSalary);

            CriteriaQuery<Double> criteriaQuery3 = builder.createQuery(Double.class);
            Root<Employee> root3 = criteriaQuery3.from(Employee.class);
            criteriaQuery3.select(builder.avg(root3.get("salary")));
            Query<Double> query3 = session.createQuery(criteriaQuery3);
            double avgSalary = query3.getSingleResult();
            System.out.println("Average Salary = " + avgSalary);

            CriteriaQuery<Long> criteriaQuery4 = builder.createQuery(Long.class);
            Root<Employee> root4 = criteriaQuery4.from(Employee.class);
            criteriaQuery4.select(builder.countDistinct(root4));
            Query<Long> query4 = session.createQuery(criteriaQuery4);
            long distinct = query4.getSingleResult();
            System.out.println("Distinct count = " + distinct);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root<Employee> empRoot = criteriaQuery.from(Employee.class);
            Root<Department> deptRoot = criteriaQuery.from(Department.class);
            criteriaQuery.multiselect(empRoot, deptRoot);
            criteriaQuery.where(builder.equal(empRoot.get("department"), deptRoot.get("id")));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> list = query.getResultList();
            for (Object[] objects : list) {
                Employee employee = (Employee) objects[0];
                Department department = (Department) objects[1];
                System.out.println("EMP NAME=" + employee.getName() + "\t DEPT NAME=" + department.getName());
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.multiselect(builder.count(root.get("name")), root.get("salary"),
                    root.get("department"));
            criteriaQuery.groupBy(root.get("salary"), root.get("department"));
            criteriaQuery.having(builder.greaterThan(root.get("salary"), 1300));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> list = query.getResultList();
            for (Object[] objects : list) {
                long count = (Long) objects[0];
                int salary = (Integer) objects[1];
                Department department = (Department) objects[2];
                System.out.println("Number of Employee = " + count + "\t SALARY=" + salary
                        + "\t DEPT NAME=" + department.getName());
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root);
            criteriaQuery.orderBy(builder.asc(root.get("salary")));
            Query<Employee> query = session.createQuery(criteriaQuery);
            List<Employee> list = query.getResultList();
            for (Employee employee : list) {
                System.out.println("EMP NAME="+employee.getName()+"\t SALARY="+employee.getSalary());
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();


            List<Long> totalDept=session.createNamedQuery("get_total_dept",Long.class).getResultList();
            System.out.println("Total Department: "+totalDept.get(0));

            List<String> deptName=session.createNamedQuery("get_dept_name_by_id",String.class)
                    .setParameter("id", 2L)
                    .getResultList();
            for (Object object : deptName) {
                System.out.println(object);
            }

            List<Department> departments=session.createNamedQuery("get_all_dept",Department.class)
                    .getResultList();
            for (Department department : departments) {
                System.out.println("ID : "+department.getId()+" \tNAME : "+department.getName());
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            List<Long> totalDept=session.createNamedQuery("get_total_dept",Long.class).getResultList();
            System.out.println("Total Department: "+totalDept.get(0));

            List<String> deptName=session.createNamedQuery("get_dept_name_by_id",String.class)
                    .setParameter("id", 2L)
                    .getResultList();
            for (Object object : deptName) {
                System.out.println(object);
            }

            List<Department> departments=session.createNamedQuery("get_all_dept",Department.class)
                    .getResultList();
            for (Department department : departments) {
                System.out.println("ID : "+department.getId()+" \tNAME : "+department.getName());
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        HibernateUtil.shutdown();
    }
}