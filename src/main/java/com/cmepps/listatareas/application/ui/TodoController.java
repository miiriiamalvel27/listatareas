package com.cmepps.listatareas.application.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmepps.listatareas.core.domain.Todo;
import com.cmepps.listatareas.core.driver_ports.ITodoService;

@Controller
public class TodoController {

    private final ITodoService todoService;

    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy");

        dateFormat.setLenient(false);

        binder.registerCustomEditor(
                Date.class,
                new CustomDateEditor(dateFormat, false)
        );
    }

    private String getLoggedInUserName() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    // Mostrar todas las tareas
    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showTodos(ModelMap model) {

        String name = getLoggedInUserName();

        model.put("todos", todoService.getTodosByUser(name));

        return "list-todos";
    }

    // Mostrar formulario para añadir tarea
    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model) {

        model.addAttribute("todo", new Todo());

        return "todo";
    }

    // Guardar una nueva tarea
    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(Todo todo) {

        todo.setId(0);
        todo.setUserName(getLoggedInUserName());

        todoService.saveTodo(todo);

        return "redirect:/list-todos";
    }

    // Mostrar formulario para editar tarea
    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(
            @RequestParam long id, ModelMap model) {

        return todoService.getTodoById(id)
                .filter(todo -> todo.getUserName()
                        .equals(getLoggedInUserName()))
                .map(todo -> {
                    model.put("todo", todo);
                    return "todo";
                })
                .orElse("redirect:/list-todos");
    }

    // Guardar los cambios de una tarea
    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(Todo todo) {

        boolean perteneceAlUsuario = todoService
                .getTodoById(todo.getId())
                .filter(t -> t.getUserName()
                        .equals(getLoggedInUserName()))
                .isPresent();

        if (perteneceAlUsuario) {
            todo.setUserName(getLoggedInUserName());
            todoService.updateTodo(todo);
        }

        return "redirect:/list-todos";
    }

    // Eliminar una tarea
    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam long id) {

        todoService.getTodoById(id)
                .filter(todo -> todo.getUserName()
                        .equals(getLoggedInUserName()))
                .ifPresent(todo ->
                        todoService.deleteTodo(todo.getId()));

        return "redirect:/list-todos";
    }
}