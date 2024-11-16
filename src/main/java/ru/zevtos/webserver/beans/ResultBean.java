package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.zevtos.webserver.entities.Result;
import ru.zevtos.webserver.services.InputService;
import ru.zevtos.webserver.services.ResultService;

import java.io.Serializable;
import java.util.List;

@Named("resultBean")
@SessionScoped
public class ResultBean implements Serializable {

    @Inject
    private ResultService resultService; // Работа с базой данных

    @Inject
    private InputService inputService; // Логика валидации и проверки
    @Getter
    @Setter
    private Result result = new Result(); // Храним объект Result
    private List<Result> results; // Список результатов из базы данных

    /**
     * Обработчик попадания точки.
     */
    public void checkHit() {
        try {
            // Валидация данных
            inputService.validateInput(result.getX(), result.getY(), result.getR());

            // Проверка попадания в область
            result.setHit(inputService.checkPoint(result));

            // Сохранение результата в базу данных
            resultService.save(result);

            // Обновляем список результатов
            loadResults();

            // Успешное сообщение
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Успех", "Точка успешно проверена!"));

            // Сброс текущего результата
            reset();

        } catch (IllegalArgumentException e) {
            // Обработка ошибок валидации
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка валидации", e.getMessage()));
        } catch (Exception e) {
            // Общая обработка ошибок
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Произошла ошибка: " + e.getMessage()));
        }
    }

    /**
     * Загружает результаты из базы данных.
     */
    public void loadResults() {
        results = resultService.findAll();
    }

    /**
     * Сбрасывает текущий результат.
     */
    public void reset() {
        result = new Result();
    }

    /**
     * Возвращает список результатов из базы данных.
     */
    public List<Result> getResults() {
        if (results == null) {
            loadResults();
        }
        return results;
    }

    /**
     * Очищает список результатов.
     */
    public void clearResults() {
        try {
            // Удаление всех результатов из базы данных
            resultService.deleteAll();

            // Очистка локального списка результатов
            results = null;

            // Уведомление пользователя об успешной очистке
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Успех", "Все результаты успешно удалены."));
        } catch (Exception e) {
            // Обработка ошибок
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Не удалось очистить результаты: " + e.getMessage()));
        }
    }

    public void deleteResult(Long id) {
        resultService.deleteById(id);
    }

}
