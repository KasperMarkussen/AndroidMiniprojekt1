package a15s.android.datamatiker.mini_projekt1;

import java.util.Date;

/**
 * Created by Marcus on 28-03-2017.
 */

public class TestData {

    public TestData() {

    }

    public ShoppingList[] TEST_LISTS = new ShoppingList[]
            {
                    new ShoppingList("list a", 1),
                    new ShoppingList("list b", 2),
                    new ShoppingList("list c", 3),
                    new ShoppingList("list d", 4),
                    new ShoppingList("list e", 5)
            };

    public Store[] TEST_STORES = new Store[]
            {
                    new Store(1,"store a", "this is store a description"),
                    new Store(2,"store b", "this is store b description"),
                    new Store(3,"store c", "this is store c description"),
                    new Store(4,"store d", "this is store d description"),
                    new Store(5,"store e", "this is store e description")
            };


    public Ware[] TEST_WARES = new Ware[]
            {
                    new Ware(1,"ware a", "1kg", 20, TEST_STORES[0].getId()),
                    new Ware(2,"ware b", "2kg", 50, TEST_STORES[0].getId(),34.95, new Date(2020,1,1)),
                    new Ware(3,"ware c", "1L", 6.5, TEST_STORES[1].getId()),
                    new Ware(4,"ware d", "1stk", 7.65, TEST_STORES[1].getId()),
                    new Ware(5,"ware e", "2L", 7.98, TEST_STORES[1].getId()),
                    new Ware(6,"ware f", "5g", 140, TEST_STORES[2].getId()),
                    new Ware(7,"ware g", "1pk", 6.98, TEST_STORES[3].getId()),
                    new Ware(8,"ware h", "1pk", 14.99, TEST_STORES[3].getId()),
                    new Ware(9,"ware i", "4stk", 19.95, TEST_STORES[4].getId()),
                    new Ware(10,"ware j", "1kg", 35.5, TEST_STORES[4].getId()),
                    new Ware(11,"ware k", "500g", 30, TEST_STORES[4].getId())
            };
    public ShoppingItem[] TEST_ITEMS = new ShoppingItem[]
            {
                    new ShoppingItem(1, TEST_WARES[0], 1, 1, TEST_STORES[TEST_WARES[0].getStoreId()-1].getName()),
                    new ShoppingItem(2, TEST_WARES[1], 4, 1, TEST_STORES[TEST_WARES[1].getStoreId()-1].getName()),
                    new ShoppingItem(3, TEST_WARES[1], 2, 2, TEST_STORES[TEST_WARES[1].getStoreId()-1].getName()),
                    new ShoppingItem(4, TEST_WARES[2], 3, 3, TEST_STORES[TEST_WARES[2].getStoreId()-1].getName()),
                    new ShoppingItem(5, TEST_WARES[0], 5, 3, TEST_STORES[TEST_WARES[0].getStoreId()-1].getName()),
                    new ShoppingItem(6, TEST_WARES[5], 1, 4, TEST_STORES[TEST_WARES[5].getStoreId()-1].getName()),
                    new ShoppingItem(7, TEST_WARES[7], 2, 4, TEST_STORES[TEST_WARES[7].getStoreId()-1].getName()),
                    new ShoppingItem(8, TEST_WARES[2], 1, 4, TEST_STORES[TEST_WARES[2].getStoreId()-1].getName()),
                    new ShoppingItem(9, TEST_WARES[0], 5, 5, TEST_STORES[TEST_WARES[0].getStoreId()-1].getName()),
                    new ShoppingItem(10, TEST_WARES[3], 6, 5, TEST_STORES[TEST_WARES[3].getStoreId()-1].getName()),
                    new ShoppingItem(11, TEST_WARES[4], 1, 5, TEST_STORES[TEST_WARES[4].getStoreId()-1].getName()),
                    new ShoppingItem(12, TEST_WARES[8], 1, 5, TEST_STORES[TEST_WARES[8].getStoreId()-1].getName())
            };
}
