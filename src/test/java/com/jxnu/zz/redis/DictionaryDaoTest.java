package com.jxnu.zz.redis;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxnu.zz.redis.config.LocalRedisConfig;

@ContextConfiguration(classes = { LocalRedisConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryDaoTest {

	@Inject
	private DictionaryDao dictionaryDao;

	@Inject
	private StringRedisTemplate redisTemplate;

	@After
	public void tearDown() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	@Test
	public void testAddWordWithItsMeaningToDictionary() {
		String meaning = "To move forward with a bounding, drooping motion.";
		String word = "lollop";
		Long index = dictionaryDao.addWordWithItsMeaningToDictionary(word, meaning);
		assertThat(index, is(notNullValue()));
		String result = dictionaryDao.popRightMeaningInDictionary(word);
		assertThat(result, is(equalTo(meaning)));
	}

	@Test
	public void shouldAddMeaningToAWordIfItExists() {
		Long index = dictionaryDao.addWordWithItsMeaningToDictionary("lollop", "To move forward with a bounding, drooping motion.");
		assertThat(index, is(notNullValue()));
		assertThat(index, is(equalTo(1L)));
		index = dictionaryDao.addWordWithItsMeaningToDictionary("lollop", "To hang loosely; droop; dangle.");
		assertThat(index, is(equalTo(2L)));
	}
}
