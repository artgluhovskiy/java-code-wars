package org.art.projects.java_code_wars.entities;

import lombok.Data;

import java.sql.Date;
import java.util.Objects;

/**
 * This class is an implementation of task order entity
 */
@Data
public class TaskOrder {

    private long orderID;
    private long userID;
    private long taskID;
    private Date regDate = new Date(System.currentTimeMillis());
    private String status;
    private long execTime;

    public TaskOrder(long user_id, long task_id, String status) {
        this.userID = user_id;
        this.taskID = task_id;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskOrder taskOrder = (TaskOrder) o;
        return orderID == taskOrder.orderID &&
                userID == taskOrder.userID &&
                taskID == taskOrder.taskID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, userID, taskID);
    }

    @Override
    public String toString() {
        return new StringBuilder("*** TaskOrder {\n")
                .append("* order ID = " + orderID + "\n")
                .append("* user_id = " + userID + "\n")
                .append("* task_id = " + taskID + "\n")
                .append("* registration date = " + regDate + "\n")
                .append("* status = " + status + "\n")
                .append("***")
                .toString();
    }
}
