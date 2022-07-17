package ayupov.ilgam.mcache.domain

enum class Source(val packageName: String) {
    TELEGRAM("org.telegram.messenger"),
    WHATSAPP("com.whatsapp"),
    VIBER("com.viber.voip"),
}
