package ru.zevtos.webserver.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.zevtos.webserver.entities.Result;

import java.util.Set;

/**
 * InputService - сервис для парсинга и валидации входных данных.
 * <p>
 * Этот класс предоставляет методы для парсинга параметров, пришедших от клиента,
 * валидации данных и проверки попадания точки в область на графике.
 */
@ApplicationScoped
public class InputService {

    private final Validator validator;

    /**
     * Конструктор класса InputService, инициализирующий валидатор для проверки данных.
     */
    public InputService() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    /**
     * Выполняет валидацию данных, переданных для объекта Result.
     *
     * @param x координата X точки.
     * @param y координата Y точки.
     * @param r радиус R.
     * @throws IllegalArgumentException если данные не проходят валидацию.
     */
    public void validateInput(double x, double y, double r) {
        Result result = new Result(null, x, y, r, false);

        // Выполнение валидации
        Set<ConstraintViolation<Result>> violations = validator.validate(result);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Result> violation : violations) {
                errorMessages.append(violation.getMessage()).append("<br>");
            }
            throw new IllegalArgumentException(errorMessages.toString());
        }
    }

    /**
     * Проверяет, попадает ли точка с заданными координатами в область.
     *
     * @param result объект Result, содержащий координаты x, y и радиус r.
     * @return true, если точка попадает в область, иначе false.
     */
    public boolean checkPoint(Result result) {
        return checkPoint(result.getX(), result.getY(), result.getR());
    }

    /**
     * Проверяет, попадает ли точка с заданными координатами в область, заданную радиусом R.
     *
     * @param x координата x точки.
     * @param y координата y точки.
     * @param r радиус R, определяющий область проверки.
     * @return true, если точка попадает в область, иначе false.
     */
    public boolean checkPoint(double x, double y, double r) {
        // Четверть круга в левом нижнем углу
        if (x <= 0 && y <= 0 && (x * x + y * y <= r * r)) {
            return true;
        }

        // Треугольник в верхнем левом углу
        if (x <= 0 && y >= 0 && y <= x + r) {
            return true;
        }

        // Прямоугольник в нижнем правом углу
        return x >= 0 && x <= r / 2 && y >= -r && y <= 0;
    }
}