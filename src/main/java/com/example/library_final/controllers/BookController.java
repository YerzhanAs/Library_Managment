package com.example.library_final.controllers;

import com.example.library_final.entities.Books;
import com.example.library_final.entities.Users;
import com.example.library_final.services.BookService;
import com.example.library_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/book")
public class BookController {

    private BookService bookService;

    private UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public String index(Model model){
        List<Books> books=bookService.getAllBooks();

        model.addAttribute("books", books);

        return "index";
    }

    @GetMapping(value="/details/{id}")
    public String details(Model model, @PathVariable(name="id") Long id){

        model.addAttribute("currentUser", userService.currentUser());

        Books book=bookService.getBook(id);
        model.addAttribute("book", book);


        return "aboutbook";
    }

    @PostMapping(value="/savebook")
    public String saveItem(@RequestParam(name="id", defaultValue = "no id") Long id){

        Books book=bookService.getBook(id);
        if(book!=null){

            Users users=userService.currentUser();

            if(users!=null) {
                List<Books> books=users.getBooks();
                if(books==null){
                    books=new ArrayList<>();
                }
                books.add(book);

                users.setBooks(books);

                userService.saveUser(users);
                 return "redirect:/book/details/"+id+"?success";
            }
        }

        return "redirect:/book/details/"+id+"?error";
    }

    @PostMapping(value = "/delete")
    public String assignCategory(@RequestParam(name="id") Long userId,
                                 @RequestParam(name="book_id") Long bookId){

        System.out.println(userId+" "+ bookId);

        Books book=bookService.getBook(bookId);
        if(book!=null){

            Users users=userService.currentUser();

            if(users!=null) {
                List<Books> books=users.getBooks();
                if(books==null){
                    books=new ArrayList<>();
                }
                books.remove(book);

                users.setBooks(books);

                userService.saveUser(users);
                return "redirect:/book/mybook?success";
            }
        }

        return "redirect:/book/mybook?error";
    }

    @GetMapping(value = "/mybook")
    public String mybook(Model model){
        Users users=userService.currentUser();

        model.addAttribute("mybooks", users);

        return "mybook";
    }

    @GetMapping(value="/addbook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addBook(Model model){

        return "addbook";
    }

    @PostMapping(value = "/addbook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addItem(@RequestParam(name="book_name", defaultValue = "No Item") String book_name,
                          @RequestParam(name="book_author", defaultValue = "No Item") String book_author,
                          @RequestParam(name="book_amount",defaultValue = "0") int book_amount,
                          @RequestParam(name="book_year",defaultValue = "0") int book_year,
                          @RequestParam(name="book_url",defaultValue = "0") String book_url) {

             Books books=new Books();

             books.setName(book_name);
             books.setAuthor(book_author);
             books.setAmount(book_amount);
             books.setYear(book_year);
             books.setUrl(book_url);

             bookService.saveBook(books);



        return "redirect:/book/all";

    }

    @PostMapping(value="/deletebook")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteItem(@RequestParam(name="id", defaultValue = "0") Long id){

        System.out.println(id);
        Books books=bookService.getBook(id);
        if(books!=null){
            bookService.deleteBook(books);
        }

        return "redirect:/book/all";
    }

    @GetMapping(value="/editbook/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editBook(Model model, @PathVariable(name="id") Long id){

        Books books=bookService.getBook(id);
        model.addAttribute("books", books);


        return "editbook";
    }


}
