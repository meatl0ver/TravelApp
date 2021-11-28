package raui.imashev.travelapp.utils

class AirportNameUtil {
    fun createName(str: String): String {
        return when (str) {
            "SVO" -> "Sheremetyevo (Moscow)"
            "HND" -> "Haneda (Tokio)"
            "NRT" -> "Narita (Narita)"
            "EWR" -> "Newark Liberty (Newark)"
            "DME" -> "Domodedovo (Moscow)"
            "DOH" -> "Hamad (Doha)"
            "JFK" -> "John F. Kennedy (New-York)"
            "LHR" -> "Heathrow (London)"
            "FRA" -> "Frankfurt (Frankfurt)"
            else -> "Mistake"
        }
    }
}