package com.example.util;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtil {
	public static int random5Number() {
		return ThreadLocalRandom.current().nextInt(10000, 99999);
	}
}
