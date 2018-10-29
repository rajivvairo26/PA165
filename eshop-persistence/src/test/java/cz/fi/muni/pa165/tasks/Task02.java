package cz.fi.muni.pa165.tasks;

import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

//adding context configuration and configure it
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;
    //adding new category
    private Category kitchen = new Category();
    private Category electro = new Category();
    //adding new produt
    private Product flashlight = new Product();
    private Product kitchenRobot = new Product();
    private Product plate = new Product();

    //run before any other test class, store in the db
    @BeforeClass
    public void dataTest() {
        EntityManager emWork = emf.createEntityManager();
        emWork.getTransaction().begin();
        //store category kitchen & electro
        kitchen.setName("Kitchen");
        emWork.persist(kitchen);
        electro.setName("Electro");
        emWork.persist(electro);

        //store product flashlight
        flashlight.setName("Flashlight");
        flashlight.addCategory(electro);
        emWork.persist(flashlight);
        //store product kitchen robot
        kitchenRobot.setName("Kitchen Robot");
        kitchenRobot.addCategory(kitchen);
        kitchenRobot.addCategory(electro);
        emWork.persist(kitchenRobot);
        //store product plate
        plate.setName("Plate");
        plate.addCategory(kitchen);
        emWork.persist(plate);
        //entity manager commit and close
        emWork.getTransaction().commit();
        emWork.close();

    }

    //test for not null annotation task5
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testDoesntSaveNullName() {
        EntityManager emWork = emf.createEntityManager();
        emWork.getTransaction().begin();
        Product product = new Product();
        emWork.persist(product);
        emWork.getTransaction().commit();
        emWork.close();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testDoesntSaveNullName2() {
        EntityManager emWork = emf.createEntityManager();
        emWork.getTransaction().begin();
        Category category = new Category();
        emWork.persist(category);
        emWork.getTransaction().commit();
        emWork.close();
    }

    //test class for category kitchen
    @Test
    public void testKitchen() {
        EntityManager emWork = emf.createEntityManager();
        Category found = emWork.find(Category.class, kitchen.getId());
        // Assert.assertEquals(found.getName(), "Kitchen");
        Assert.assertEquals(found.getProducts().size(), 2);
        assertContainsProductWithName(found.getProducts(), "Plate");
        assertContainsProductWithName(found.getProducts(), "Kitchen Robot");

        emWork.close();
    }

    //test class for category electro
    @Test
    public void testElectro() {
        EntityManager emWork = emf.createEntityManager();
        Category found = emWork.find(Category.class, electro.getId());
        // Assert.assertEquals(found.getName(), "Electro");
        Assert.assertEquals(found.getProducts().size(), 2);
        assertContainsProductWithName(found.getProducts(), "Flashlight");
        assertContainsProductWithName(found.getProducts(), "Kitchen Robot");

        emWork.close();
    }

    //test class for product flashlight
    @Test
    public void testFlashlight() {
        EntityManager emWork = emf.createEntityManager();
        Product found = emWork.find(Product.class, flashlight.getId());
        // Assert.assertEquals(found.getName(), "Flashlight");
        Assert.assertEquals(found.getCategories().size(), 1);
        assertContainsCategoryWithName(found.getCategories(), "Electro");

        emWork.close();
    }

    //test class for product Kitchen Robot
    @Test
    public void testKitchenRobot() {
        EntityManager emWork = emf.createEntityManager();
        Product found = emWork.find(Product.class, kitchenRobot.getId());
        //Assert.assertEquals(found.getName(), "Kitchen Robot");
        Assert.assertEquals(found.getCategories().size(), 2);
        assertContainsCategoryWithName(found.getCategories(), "Electro");
        assertContainsCategoryWithName(found.getCategories(), "Kitchen");
        emWork.close();
    }

    //test class for prduct Plate
    @Test
    public void testPlate() {
        EntityManager emWork = emf.createEntityManager();
        Product found = emWork.find(Product.class, plate.getId());
        //Assert.assertEquals(found.getName(), "Plate");
        Assert.assertEquals(found.getCategories().size(), 1);
        assertContainsCategoryWithName(found.getCategories(), "Kitchen");
        emWork.close();
    }

    private void assertContainsCategoryWithName(Set<Category> categories,
            String expectedCategoryName) {
        for (Category cat : categories) {
            if (cat.getName().equals(expectedCategoryName)) {
                return;
            }
        }

        Assert.fail("Couldn't find category " + expectedCategoryName + " in collection " + categories);
    }

    private void assertContainsProductWithName(Set<Product> products,
            String expectedProductName) {

        for (Product prod : products) {
            if (prod.getName().equals(expectedProductName)) {
                return;
            }
        }

        Assert.fail("Couldn't find product " + expectedProductName + " in collection " + products);
    }

}
