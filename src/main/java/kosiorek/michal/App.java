package kosiorek.michal;

import kosiorek.michal.connection.DbConnection;
import kosiorek.michal.model.Movie;
import kosiorek.michal.repository.MovieRepository;
import kosiorek.michal.service.MenuService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DbConnection conn = DbConnection.getInstance();


        MenuService menuService = new MenuService();
        menuService.mainMenu();

    }
}
