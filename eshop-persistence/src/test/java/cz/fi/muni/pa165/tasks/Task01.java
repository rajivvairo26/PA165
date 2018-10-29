package cz.fi.muni.pa165.tasks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;

//adding context configuration and configure it
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task01 extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void categoryTest() {
        EntityManager emWork = emf.createEntityManager();
        emWork.getTransaction().begin();
        Category cat = new Category();
        cat.setName("Test");
        emWork.persist(cat);
        emWork.getTransaction().commit();
        emWork.close();
        //TODO under this line: create a second entity manager in categoryTest, use find method to find the category and assert its name.
        //task 1 adding code
        emWork = emf.createEntityManager();
        Category found = emWork.find(Category.class, cat.getId());
        Assert.assertEquals(found.getName(), "Test");
        emWork.close();

    }

}
