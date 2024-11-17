package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.zevtos.webserver.entities.Result;

import java.io.Serializable;

@Named("resultBean")
@SessionScoped
public class ResultBean implements Serializable {

    @Getter
    @Setter
    private Result result = new Result(); // Текущий объект Result

    @Inject
    private ResultListBean resultListBean; // Бин для управления списком результатов

    /**
     * Обработчик попадания точки.
     */
    public void checkHit() {
        // Проверка попадания в область
        result.setHit(checkPoint());

        // Сохранение результата
        saveResult();
    }

    /**
     * Сохраняет результат, добавляя его в ResultListBean.
     */
    private void saveResult() {
        resultListBean.addResult(result);
    }

    /**
     * Проверяет, попадает ли точка в область.
     *
     * @return true, если точка попадает в область, иначе false.
     */
    private boolean checkPoint() {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

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

    /**
     * Очищает все результаты.
     */
    public void clearResults() {
        resultListBean.clearResults();
    }

    /**
     * Удаляет результат по идентификатору.
     *
     * @param id идентификатор результата для удаления.
     */
    public void deleteResult(Long id) {
        resultListBean.deleteResult(id);
    }
}
