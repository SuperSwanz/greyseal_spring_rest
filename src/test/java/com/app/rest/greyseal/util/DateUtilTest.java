package com.app.rest.greyseal.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class DateUtilTest {


    @Test
    public void getDate() {
        final Date dt = new Date();
        final Date newDate = DateUtil.getDate(dt);
        assertThat(dt.getTime()).isEqualTo(newDate.getTime());
        assertThat(dt).isEqualTo(newDate);
    }

    @Test
    public void getDateWhenNull() {
        final Date dt = null;
        final Date newDate = DateUtil.getDate(dt);
        assertThat(newDate).isNotNull();
        assertThat(newDate).isExactlyInstanceOf(Date.class);
    }
}
