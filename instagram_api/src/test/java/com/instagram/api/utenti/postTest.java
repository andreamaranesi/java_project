package com.instagram.api.utenti;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class postTest extends post{

	@Test
	void test() {
		String descrizione="prova descrizione #hashtag1\n#hashtag2; #hashtag3";
		ArrayList<String> hashtag=super.hashtag(descrizione);
		assertAll(()->assertEquals(hashtag.get(0),"hashtag1"), ()->assertEquals(hashtag.get(1),"hashtag2"), ()->assertEquals(hashtag.get(2), "hashtag3"));
	}

}
