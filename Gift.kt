import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 마타, 한해동안 정말 고생하셨습니다.
 *
 * 선물은 제가 좋아하는 이더리움이며, kotlmata를 사용하고 공감해보기 위해 기획하였습니다.
 * 다음과 같이 만든 이유는 여러 의미가 있습니다.
 *
 * 1.  privateKey 를 얻기 어려움
 * 2.  그래서 이더리움을 얻기 어려움
 * 3.  그래서 이더리움을 팔기 어려움
 * 4.  그래서 나도 모르는 사이에 가치가 상승함
 * 5.  그렇게 시간이 흘러 얻지 못한채로 내년이 됨
 * 6.  그래서 갑자기 다음해가 되었는데 작년 마니또가 생각남
 * 7.  그래서 시세를 봤더니..? 대박
 * 8.  private key를 얻는 과정이 즐거워짐
 * 9.  판매 후 맛있는 것을 사먹음
 * 10. 이렇게 깊은 뜻이..
 * 11. 추가적으로 코드 만들면서 이해해보고 싶었습니다.
 * 12. 저같이 잘 모르는 사용자가 쉽게 이해할 수 있을까? 궁금했습니다.
 * 13. 한해동안 이해하고, 논쟁하고, 코딩하고, 개발하고, 가르치고 고생많이 하셨습니다.
 * 14. Happy new year! double twenty!
 *
 * <정보>
 * public key : 0xf8c94ff2dcd051ceD05877ffF133aF09f185564f
 * private key : ?
 *
 * 참고 : 현재 업비트에서 구매는 하였으나, 해킹사건으로 인해 출금이 불가능합니다. 추후에 입금하겠습니다.
 *       PASSWORD 에 개인적으로 받은 값을 입력하고 실행하시면 PrivateKey 를 얻을 수 있습니다.
 */

internal const val logLevel = 1

const val PASSWORD = "" /** <------ 여기에 개인적으로 받은 값을 넣어주세요 */
const val ENCRYPTED_VALUE =
    "JaTSg/JrOce7gJcKoZs8A+yW3PgnS+F33AGDXpiATsBJHg+naV/IJmACuqR0FweSIfhr5fPMDzrFmVpUtIUD0IyV0CNW9w/nDGWV1dBI8Is="

fun main(args: Array<String>) {
    val daemonName = "Manito#"
    Kotlmata.config {
        print.debug {
            print(it)
        }
    }

    val core = KotlmataDaemon("$daemonName", logLevel, "Mata thread") {
        var value = "" // immutable로 넘겨야하는데 이런 장난을 하다니?
        on start {
            Thread.currentThread().priority = Thread.MAX_PRIORITY
        }
        "마" {
            value += "Words "
        }
        "Is"{
            entry via 타::class action {
                value += "are "
            }
        }
        "Best"{
            entry via The::class action {
                value += "too "
            }
        }
        "With"{
            entry via Programmer::class action {
                value += "cheap to express "
            }
        }
        "Mobile"{
            entry via Channel::class action {
                value += "my appreciation "
            }
        }
        "Thanks"{
            entry via End::class action {
                value += "this year"
                printKey(value)
                try {
                    printSecretKey(ENCRYPTED_VALUE.decrypt(PASSWORD + value))
                } catch (e: BadPaddingException) {
                    print("올바른 password가 필요합니다.")
                }
            }
        }

        "마" x 타::class %= "Is"
        "Is" x The::class %= "Best"
        "Best" x Programmer::class %= "With"
        "With" x Channel::class %= "Mobile"
        "Mobile" x End::class %= "Thanks"

        start at "마"
    }

    with(core) {
        run()
        input(타())
        input(The())
        input(Programmer())
        input(Channel())
        input(End())
    }

    // site : https://github.com/matamanito/MataGift/blob/master/Gift.kt
}

private fun printKey(value: String) {
    println("\n------------------key------------------\n")
    println(PASSWORD + value)
    println("\n------------------key------------------\n")
}

private fun printSecretKey(value: String) {
    println("\n------------------SecretKey------------------\n")
    println(value)
    println("\n------------------SecretKey------------------\n")
}

fun String.decrypt(password: String): String {
    val realPassword = password.substring(0, 16)
    val secretKeySpec = SecretKeySpec(realPassword.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = realPassword.toCharArray()
    for (i in charArray.indices) iv[i] = charArray[i].toByte()
    val ivParameterSpec = IvParameterSpec(iv)
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
    val decryptedByteValue = cipher.doFinal(Base64.getDecoder().decode(this))
    return String(decryptedByteValue)
}

private class 타
private class The
private class Programmer
private class Channel
private class End
