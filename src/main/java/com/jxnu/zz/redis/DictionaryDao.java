package com.jxnu.zz.redis;

import javax.inject.Inject;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryDao {

//	private static final String ALL_UNIQUE_WORDS = "all-unique-words";

	private StringRedisTemplate redisTemplate;

	@Inject
	public DictionaryDao(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Long addWordWithItsMeaningToDictionary(String word, String meaning) {
		Long index = redisTemplate.opsForList().rightPush(word, meaning);
		return index;
	}

	public String popRightMeaningInDictionary(String word) {
		String meaning = redisTemplate.opsForList().rightPop(word);
		return meaning;
	}
}
