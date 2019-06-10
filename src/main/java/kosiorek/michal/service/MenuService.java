package kosiorek.michal.service;

import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.mailers.MailUtils;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.LoyaltyCard;
import kosiorek.michal.model.Movie;
import kosiorek.michal.model.SalesStand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuService {

    private static final int NUMBEROFTICKETSREQUIREDFORLOYALTYCARD = 5;

    private CustomerService customerService = new CustomerService();
    private MovieService movieService = new MovieService();
    private SalesStandService salesStandService = new SalesStandService();
    private LoyaltyCardService loyaltyCardService = new LoyaltyCardService();
    private UserDataService userDataService = new UserDataService();
    private FileReaderService fileReaderService = new FileReaderService();

    public void mainMenu() {

        String menu;
        while (true) {
            try {
                do {
                    displayMenu();
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":
                            option1();
                            break;
                        case "2":
                            option2();
                            break;
                        case "3":
                            option3();
                            break;
                        case "4":
                            option4();
                            break;
                        case "5":
                            option5();
                            break;
                        case "6":
                            option6();
                            break;
                        case "7":
                            userDataService.close();
                            System.out.println("The End");
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (MyException e) {
                System.out.println(e.getExceptionInfo().getExceptionMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("Menu - enter the number:");
        System.out.println("1 - Add new customer.");
        System.out.println("2 - Add new movies from file.");
        System.out.println("3 - Show all customers.");
        System.out.println("4 - Show all movies.");
        System.out.println("5 - Buy a ticket.");
        System.out.println("6 - Send an email with customer's purchase history to customer.");


        System.out.println("7 - Exit");
    }

    private void option1() {

        Customer customer = Customer.builder().name(userDataService.getString("Enter name:", "[A-Z ]+"))
                .surname(userDataService.getString("Enter surname:", "[A-Z ]+"))
                .age(userDataService.getInt("Enter age:"))
                .email(userDataService.getEmail("Enter email:"))
                .build();
        customerService.addCustomer(customer);


    }

    private void option2() {

        movieService.addMovieList(fileReaderService.readMoviesFromFile());

    }

    private void option3() {

        List<Customer> customers = customerService.getAllCustomers();
        displayCustomers(customers);
        showEditCustomersMenu();

        int option;
        String menu;
        while (true) {
            try {
                do {
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":
                            option = userDataService.getInt("Enter number of customer to edit:");
                            editCustomerOption1(customers.get(option - 1));
                            break;
                        case "2":
                            option = userDataService.getInt("Enter number of customer to delete:");
                            editCustomerOption2(customers.get(option - 1));
                            break;
                        case "3":
                            customers.sort(Comparator.comparing(Customer::getName));
                            displayCustomers(customers);
                            break;
                        case "4":
                            customers.sort(Comparator.comparing(Customer::getSurname));
                            displayCustomers(customers);
                            break;
                        case "5":
                            customers.sort(Comparator.comparing(Customer::getAge));
                            displayCustomers(customers);
                            break;

                        case "6":
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (MyException e) {
                System.out.println(e.getExceptionInfo());
            }
        }


    }

    private void displayCustomers(List<Customer> customers) {

        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i));
        }
    }


    private void showEditCustomersMenu() {
        System.out.println("1 - Edit customer with id:");
        System.out.println("2 - Delete customer with id:");
        System.out.println("3 - Sort customers by name:");
        System.out.println("4 - Sort customers by surname:");
        System.out.println("5 - Sort customers by age:");
        System.out.println("6 - Return to main menu.");
    }

    private void editCustomerOption1(Customer customer) {


        customer.setName(userDataService.getString("Edit name:", "[A-Z ]+"));
        customer.setSurname(userDataService.getString("Edit surname:", "[A-Z ]+"));
        customer.setAge(userDataService.getInt("Edit age:"));
        customer.setEmail(userDataService.getEmail("Edit email:"));

        customerService.editCustomer(customer);

    }


    public void editCustomerOption2(Customer customer) {
        customerService.deleteCustomer(customer);
    }

    private void option4() {

        List<Movie> movies = movieService.getAllMovies();
        displayMovies(movies);
        showEditMoviesMenu();
        String menu;
        menu = userDataService.getString("Enter number:", "");
        int option = 0;


        while (true) {
            try {
                do {
                    switch (menu) {
                        case "1":
                            option = userDataService.getInt("Enter number of movie to edit:");
                            editMoviesOption1(movies.get(option - 1));
                            break;
                        case "2":
                            option = userDataService.getInt("Enter number of movie to remove:");
                            editMoviesOption2(movies.get(option - 1));
                            break;

                        case "3":
                            movies.sort(Comparator.comparing(Movie::getTitle));
                            displayMovies(movies);
                            break;

                        case "4":
                            movies.sort(Comparator.comparing(Movie::getGenre));
                            displayMovies(movies);
                            break;

                        case "5":
                            movies.sort(Comparator.comparing(Movie::getDuration));
                            displayMovies(movies);
                            break;

                        case "6":
                            return;

                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (MyException e) {
                System.out.println(e.getExceptionInfo());
            }
        }
    }

    private void displayMovies(List<Movie> movies) {

        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
    }

    private void showEditMoviesMenu() {
        System.out.println("1 - Edit movie with id:");
        System.out.println("2 - Delete movie with id:");
        System.out.println("3 - Sort movies by title:");
        System.out.println("4 - Sort movies by genre:");
        System.out.println("5 - Sort movies by duration:");
        System.out.println("6 - Return to main menu.");
    }

    private void editMoviesOption1(Movie movie) {


        movie.setTitle(userDataService.getString("Edit title: ", "[A-Z ]+"));
        movie.setGenre(userDataService.getString("Edit title: ", "[A-Z- ]+"));
        movie.setPrice(userDataService.getBigDecimal("Edit price:"));
        movie.setDuration(userDataService.getInt("Edit duration:"));
        movie.setReleaseDate(userDataService.getDate("Edit date:"));

        movieService.editMovie(movie);

    }

    private void editMoviesOption2(Movie movie) {
        movieService.deleteMovie(movie);
    }

    private Customer getCustomerFromDatabase() {

        String name = userDataService.getString("Enter name of customer:", "[A-Z ]+");
        String surname = userDataService.getString("Enter surname of customer:", "[A-Z ]+");
        String email = userDataService.getEmail("Enter email of customer:");

        Customer customer = customerService.getCustomerByNameSurnameEmail(name, surname, email);

        return customer;
    }

    private List<LocalDateTime> getListOfAvailableTimes(LocalDate projectionDate) {

        List<LocalDateTime> ticketTimes = new ArrayList<>();

        LocalDateTime current = LocalDateTime.of(projectionDate, LocalTime.of(8, 0));


        if (projectionDate.isEqual(LocalDate.now())) {
            for (int i = 0; i < 30; i++) {
                if (current.isAfter(LocalDateTime.now())) {
                    ticketTimes.add(current);
                }
                current = current.plusMinutes(30);
            }
        } else {
            for (int i = 0; i < 30; i++) {
                ticketTimes.add(current);
                current = current.plusMinutes(30);
            }
        }

        return ticketTimes;
    }

    private void displayTicketTimes(List<LocalDateTime> ticketTimes) {
        for (int i = 0; i < ticketTimes.size(); i++) {
            System.out.println((i + 1) + ". " + ticketTimes.get(i));
        }
    }

    private void askAndCreateLoyaltyCard(Customer customer) {
        if (userDataService.getBoolean("Do you want to create a loyalty card?")) {
            loyaltyCardService.addLoyaltyCardToCustomer(customer);
        }

    }

    private void option5() {

        Customer customer = getCustomerFromDatabase();

        List<Movie> movies = movieService.getAllMovies();
        displayMovies(movies);
        int option = userDataService.getInt("Enter number of movie you want to buy ticket for:");
        Movie movie = movies.get(option - 1);
        LocalDate date;
        LocalDate releaseDate = movie.getReleaseDate();
        do {
            date = userDataService.getDate("Enter date of ticket - must be equal or later than release date, which is: " + releaseDate.toString() + " Today is: " + LocalDate.now().toString());
        } while (date.compareTo(releaseDate) < 0 || date.compareTo(LocalDate.now()) < 0);

        List<LocalDateTime> times = getListOfAvailableTimes(date);
        displayTicketTimes(times);
        int optionForTime = userDataService.getInt("Enter number of time you want to buy ticket for:");
        LocalDateTime timeForTicket = times.get(optionForTime - 1);

        SalesStand salesStand = SalesStand.builder().customerId(customer.getId()).movieId(movie.getId()).startDateTime(timeForTicket).build();
        salesStandService.buyTicket(salesStand);

        BigDecimal discount = BigDecimal.ZERO;

        if (!customerService.hasLoyaltyCard(customer)) {
            if (salesStandService.getNumberOfTicketsBoughtByCustomer(customer) >= NUMBEROFTICKETSREQUIREDFORLOYALTYCARD) {
                askAndCreateLoyaltyCard(customer);
            }
        } else {
            LoyaltyCard loyaltyCard = loyaltyCardService.getLoyaltyCardById(customer.getLoyaltyCardId());
            int number = loyaltyCard.getMoviesNumber();
            loyaltyCard.setMoviesNumber(number + 1);
            loyaltyCardService.editLoyaltyCard(loyaltyCard);
            discount = loyaltyCard.getDiscount();
        }

        BigDecimal priceAfterDiscount = movie.getPrice().multiply(BigDecimal.ONE.subtract(discount));

        String email = MailUtils.generateHTMLEmailFromTicketData(customer.getSurname(), movie.getTitle(), priceAfterDiscount, salesStand);

        MailUtils.sendEmailWithJavaxMail(email, "Ticket", customer.getEmail());

    }


    private void option6() {

        Customer customer = getCustomerFromDatabase();
        List<SalesStand> tickets = salesStandService.getTicketsBoughtByCustomer(customer);

        if (userDataService.getBoolean("Do you want to filter by date range? y/n")) {
            LocalDate dateFrom = userDataService.getDate("Enter date from: yyyy-mm-dd");
            LocalDate dateTo = userDataService.getDate("Enter date to: yyyy-mm-dd");

            tickets = tickets.stream().filter(salesStand -> salesStand.getStartDateTime().toLocalDate().compareTo(dateFrom) >= 0 && salesStand.getStartDateTime().toLocalDate().compareTo(dateTo) <= 0).collect(Collectors.toList());
        }

        if (userDataService.getBoolean("Do you want to filter by genre? y/n")) {
            String genre = userDataService.getString("Enter genre:", "[A-Za-z- ]+");
            tickets = tickets.stream().filter(salesStand -> {
                Movie movie = movieService.getMovieById(salesStand.getMovieId());
                if (movie.getGenre().equals(genre)) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        }

        if (userDataService.getBoolean("Do you want to filter by duration? y/n")) {
            int durationFrom = userDataService.getInt("Enter duration from:");
            int durationTo = userDataService.getInt("Enter duration to:");

            tickets = tickets.stream().filter(salesStand -> {
                Movie movie = movieService.getMovieById(salesStand.getMovieId());
                if (movie.getDuration() >= durationFrom && movie.getDuration() <= durationTo) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        }

        Map<Integer, Movie> movieTitles = tickets
                .stream()
                .collect(Collectors.toMap(ticket -> ticket.getMovieId(), ticket -> movieService.getMovieById(ticket.getMovieId())));


        String email = MailUtils.generateHTMLEmailFromTicketList(tickets, movieTitles);

        MailUtils.sendEmailWithJavaxMail(email, "Ticket History", customer.getEmail());

    }

}
