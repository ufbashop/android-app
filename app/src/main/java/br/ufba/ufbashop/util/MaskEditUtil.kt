package br.ufba.ufbashop.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object MaskEditUtil {

    const val FORMAT_PHONE = "(###) #####-####"
    const val FORMAT_HOUR = "##:##"

    /**
     * Método que deve ser chamado para realizar a formatação
     *
     * @param editText
     * @param mask
     * @return
     */
    fun makeMask(editText: EditText, mask:String) {
        editText.addTextChangedListener(this.mask(editText, mask))
    }
    private fun mask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            var isUpdating: Boolean = false
            var old = ""

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                val mascara = StringBuilder("")
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara.append(m)
                        continue
                    }
                    if(i < str.length)
                        mascara.append(str[i])
                    else
                        break
                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara.toString())
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    fun unmask(s: String): String {
        // The app uses only number-only strings.
        return s.replace("[^\\d+]".toRegex(), "")
    }
}