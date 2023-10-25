package com.hejow.gram.common;

import static org.junit.jupiter.api.Assertions.*;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.junit.jupiter.api.Test;

class JasyptConfigTest {
	@Test
	void encryptString_Success_EqualsWithDecrypted() {
		// given
		String target = "hejow";
		PBEStringCleanablePasswordEncryptor encryptor = initEncryptor();

		// when
		String encrypted = encryptor.encrypt(target);

		// then
		assertEquals(target, encryptor.decrypt(encrypted)); // true
	}

	@Test
	void isEncryptedAlwaysSame_Fail_ByDifferentSalt() {
		// given
		String target = "john";
		PBEStringCleanablePasswordEncryptor encryptor = initEncryptor();

		// when
		String encrypted = encryptor.encrypt(target);

		// then
		assertAll(
			() -> assertNotEquals(encrypted, encryptor.encrypt(target)),
			() -> assertNotEquals(encrypted, encryptor.encrypt(target)),
			() -> assertNotEquals(encrypted, encryptor.encrypt(target))
		);
	}

	@Test
	void sameEncrypted_Success_WithFixedSaltGenerator() {
		// given
		String target = "moon";
		String salt = "fixedSalt";
		StandardPBEStringEncryptor encryptor = initEncryptor();
		encryptor.setSaltGenerator(new StringFixedSaltGenerator(salt));

		// when
		String encrypted = encryptor.encrypt(target);

		// then
		assertAll(
			() -> assertEquals(encrypted, encryptor.encrypt(target)),
			() -> assertEquals(encrypted, encryptor.encrypt(target)),
			() -> assertEquals(encrypted, encryptor.encrypt(target))
		);
	}

	private StandardPBEStringEncryptor initEncryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("password");
		return encryptor;
	}
}
