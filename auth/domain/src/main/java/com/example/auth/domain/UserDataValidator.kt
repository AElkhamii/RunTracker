package com.example.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }

    fun isValidEmail(email: String): Boolean{
        return patternValidator.matches(email.trim())
    }

    fun isValidPassword(password: String): PasswordValidationState{
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowercaseCharacter = password.any { it.isLowerCase() }
        val hasUppercaseCharacter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasLowerCaseCharacter = hasLowercaseCharacter,
            hasUpperCaseCharacter = hasUppercaseCharacter
        )
    }
//    Another method for validation password is using Regx, for more info search for it in chat GPT
//    fun isValidPassword(password: String): Boolean {
//        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])\\S{8,}$"
//        return Regex(passwordPattern).matches(password)
//    }
}