package com.example.demo.query;

import com.example.demo.utils.constants.LogAction;
import com.example.demo.utils.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;

import static com.example.demo.utils.constants.LogAction.ALL;

public class HistoryLogQuery {
    private String action;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;

    private String queryByAction() {
        String query = "";
        if (StringUtils.isNotBlank(action)) {
            if (ALL.getNote().equals(action)) {
                query = " AND ACTION IN " + LogAction.convertListToInClause();
            } else {
                query = " AND ACTION = '" + action + "'";
            }
        }
        return query;
    }


    private String queryByCreatedAt() {
        String query = "";
        if (dateTimeFrom == null) {
            dateTimeFrom = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        }
        if (dateTimeTo == null) {
            dateTimeTo = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        }
        String dateFromQuery = DateUtil.convertToString(dateTimeFrom, DateUtil.DB_FORMAT);
        String dateToQuery = DateUtil.convertToString(dateTimeTo, DateUtil.DB_FORMAT);
        query = " AND CREATED_AT >= '" + dateFromQuery + "' AND CREATED_AT <= '" + dateToQuery + "'";
        return query;
    }

    public String getQueryCondition() {
        return " WHERE 1=1 "
                + queryByAction()
                + queryByCreatedAt();
    }

    public HistoryLogQuery() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }
}
