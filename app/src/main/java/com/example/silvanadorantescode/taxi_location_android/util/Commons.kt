package com.example.silvanadorantescode.taxi_location_android.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.App
import com.google.android.gms.common.util.IOUtils


import de.hdodenhof.circleimageview.CircleImageView

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLConnection
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

object Commons {
    private val TAG = "Commons"



    /*Width Display pixelex*/
    val widthDisplay: Int
        get() = App.getAppContext().resources.displayMetrics.widthPixels

    /*Height Display pixeles*/
    val heightDisplay: Int
        get() = App.getAppContext().resources.displayMetrics.heightPixels

    val country: List<String>
        get() {
            val locales = Locale.getAvailableLocales()
            val countries = ArrayList<String>()
            val hasCountry = HashMap<String, String>()
            for (locale in locales) {
                val country = locale.displayCountry

                if (country != "")
                    hasCountry[country] = country

            }
            for ((_, value) in hasCountry) {
                countries.add(value)
            }

            Collections.sort(countries)

            return countries
        }

    val outputMediaFile: File?
        get() {
            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyTaxi")

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return File(mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg")
        }

    val outputMediaFileVideo: File?
        get() {
            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), "Riservi")

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return File(mediaStorageDir.path + File.separator +
                    "VIDEO_" + timeStamp + ".mp4")
        }

    fun getGradientDrawable(view: View, color: Int): GradientDrawable {
        val drawable = view.background as GradientDrawable
        drawable.setColor(color)
        return drawable
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = App.getAppContext().resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun pxToDp(px: Float): Float {
        val metrics = App.getAppContext().resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun hideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun closeKeyboard(activity: Activity) {

        var view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }


    fun getPositionCountry(country: String): Int {
        var position = 0
        val countries = country
        for (i in countries.indices) {
            if (countries[i].equals(country)) {
                position = i
            }
        }
        return position
    }

    fun getColor(color: Int): Int {
        val result: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.get().baseContext.getColor(color)
        else
            result = App.get().baseContext.resources.getColor(color)
        return result
    }

    fun getColor(color: Int, theme: Resources.Theme): Int {
        val result: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.get().baseContext.resources.getColor(color, theme)
        else
            result = App.get().baseContext.resources.getColor(color)
        return result
    }

    fun getScreenOrientation(activity: Activity): Int {
        val getOrient = activity.windowManager.defaultDisplay
        var orientation = Configuration.ORIENTATION_UNDEFINED
        if (getOrient.width == getOrient.height) {
            orientation = Configuration.ORIENTATION_SQUARE
        } else {
            if (getOrient.width < getOrient.height) {
                orientation = Configuration.ORIENTATION_PORTRAIT
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE
            }
        }
        return orientation
    }


    fun getDrawable(drawable: Int): Drawable? {
        val result: Drawable?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getDrawable(drawable)
        else
            result = App.getAppContext().resources.getDrawable(drawable)
        return result
    }

    fun getStringArray(string: Int): Array<out String>? {

        return App.getAppContext().resources.getStringArray(string)
    }

    fun getString(string: Int): String {
        val result: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getString(string)
        else
            result = App.getAppContext().getString(string)
        return result
    }

    fun getStringFormat(string: Int, format: Any): String {
        val result: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            result = App.getAppContext().getString(string, format)
        else
            result = App.getAppContext().getString(string, format)
        return result
    }




    fun dateToWallHour(date: String): String {

        return getStringHour(date)
    }

    fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols(Locale("es", "ES"))
        val months = dfs.months
        if (num >= 0 && num <= 12) {
            month = months[num - 1]
        }
        return month
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToString(d: Date?): String {
        if (d != null) {
            val df = SimpleDateFormat("MM/dd/yyyy")
            return df.format(d)
        }
        return getString(R.string.undefined)
    }


    fun getBitmapFromView(view: View): Bitmap {
        //Define a bitmap with the same size as the view
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null)
        //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        else
        //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }


    fun getStringDate(string: String): String {
        try {
            val format = SimpleDateFormat("dd-MM-yyyy", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDatetwo(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM-dd-yyyy", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDateOne(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM dd, yyyy", Resources.getSystem().configuration.locale)
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "Es"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }







    fun getStringDateFive(string: String): String {
        try {

            val format = SimpleDateFormat("MMMM dd, yyyy", Resources.getSystem().configuration.locale)
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "Es"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getStringDateSix(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("us", "Es"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getStringDateTwo(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM dd, yyyy", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDayMonth(string: String): String {
        try {
            val format = SimpleDateFormat("dd MMMM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getStringYearTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("yyyy", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringMonthTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("MM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDayTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("dd", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }




    fun getStringHourCompletedTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("HH", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringMinuteTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("mm", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }



    fun getStringHourTotimeInMillis(string: String): String {
        try {
            val format = SimpleDateFormat("hh", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getStringDay(string: String): String {
        try {
            val format = SimpleDateFormat("dd", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "Es"))

            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDay0FWeek(string: String): String {
        try {
            val format = SimpleDateFormat("EEE, dd MMM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "Es"))

            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }




    fun getStringMoth(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("MM", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringMothTwo(string: String): String {
        try {
            val format = SimpleDateFormat("MMMM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd", Locale("es", "Es"))

            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getStringWeek(string: String): String {
        try {
            val format = SimpleDateFormat("MMM", Locale("es", "ES"))
            val format2 = SimpleDateFormat("MM", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringHourAMPMFive(string: String): String {

        try {
            val format = SimpleDateFormat("hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("HH:mm", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""

    }


    fun getStringHour(string: String): String {
        try {
            val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale("es", "Es"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringHourTwo(string: String): String {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.getDefault())
            val format2 = SimpleDateFormat("hh:mm:aa ", Locale.getDefault())
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringHourThree(string: String): String {

        try {
            val  tk : StringTokenizer = StringTokenizer(string)
            val date1 : String = tk.nextToken()
            val time: String = tk.nextToken()

            val format = SimpleDateFormat("hh:mm aa", Locale.US)
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.US)
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringDayAndMonth(string: String): String {

        try {
            val  tk : StringTokenizer = StringTokenizer(string)
            val date1 : String = tk.nextToken()
            val time: String = tk.nextToken()

            val format = SimpleDateFormat("dd MMM", Locale.US)
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.US)
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }





    fun getStringHourAMPM(string: String): String {

        try {
            val format = SimpleDateFormat("hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""

    }


    fun getStringHourEAMPM(string: String): String {

        try {
            val format = SimpleDateFormat("E hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)

            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""

    }

    fun getStringHourEAMPMCountry(string: String): String {

        try {
            val format = SimpleDateFormat("E hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            val tz = TimeZone.getTimeZone("America/Caracas")
            format.timeZone = tz
            println(date)


            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }



        return ""

    }

    fun getStringMonthHourAMPMCountry(string: String): String {

        try {
            val format = SimpleDateFormat("E hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es", "ES"))
            val date = format2.parse(string)
            val tz = TimeZone.getTimeZone("America/Caracas")
            format.timeZone = tz
            println(date)


            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }



        return ""

    }

    fun getStringHourAMPMCountry(string: String): String {

        try {
            val format = SimpleDateFormat("hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("es", "ES"))
            val date = format2.parse(string)
            val tz = TimeZone.getTimeZone("America/Panama")
            format.timeZone = tz
            println(date)


            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }



        return ""

    }


    fun getStringHourAMPMCountryTWO(string: String): String {

        try {
            val format = SimpleDateFormat("hh:mm aa", Locale("es", "ES"))
            val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("es", "ES"))
            val date = format2.parse(string)
            println(date)


            return format.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }



        return ""

    }




    fun normalizedString(string: String): String {
        val normalized = Normalizer.normalize(string, Normalizer.Form.NFD)
        return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    fun encodeImage(bm: Bitmap): String {
        val resize = Commons.scaleDown(bm, 500f, true)
        val baos = ByteArrayOutputStream()
        resize.compress(Bitmap.CompressFormat.PNG, 75, baos)
        val b = baos.toByteArray()

        //return Base64.encodeToString(b, Base64.DEFAULT)
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

    fun scaleDown(realImage: Bitmap, maxImageSize: Float, filter: Boolean): Bitmap {
        val ratio = Math.min(maxImageSize / realImage.width, maxImageSize / realImage.height)
        val width = Math.round(ratio * realImage.width)
        val height = Math.round(ratio * realImage.height)

        return Bitmap.createScaledBitmap(realImage, width, height, filter)
    }

    /*fun seTypeFaceTextView(view: RiserviTextView, path: Int) {
        if (Build.VERSION.SDK_INT < 23) {
            view.typeface = FontCache.getTypeface(App.getAppContext(), Commons.getString(path))
        } else {
            view.typeface = FontCache.getTypeface(App.getAppContext(), Commons.getString(path))
        }
    }*/

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            val runningProcesses = am.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.packageName) {
                            isInBackground = false
                        }
                    }
                }
            }
        } else {
            val taskInfo = am.getRunningTasks(1)
            val componentInfo = taskInfo[0].topActivity
            if (componentInfo?.packageName == context.packageName) {
                isInBackground = false
            }
        }
        return isInBackground
    }

    fun isVideoFile(path: String): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType != null && mimeType.startsWith("video")
    }

    fun createThumbnailAtTime(filePath: String, timeInSeconds: Long): Bitmap {
        val mMMR = MediaMetadataRetriever()
        mMMR.setDataSource(filePath)
        //api time unit is microseconds
        return mMMR.getFrameAtTime(timeInSeconds * 1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    }

    fun changeFont(textView: TextView, style: Int) {
        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(App.getAppContext(), style)
        } else {
            textView.setTextAppearance(style)
        }
    }

    fun isStringNull(string: String?): String {
        return string ?: ""
    }

    fun loadImageGlide(url: String, view: ImageView) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(R.drawable.background_trans).error(R.drawable.background_trans)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImageGlideOverride(url: String, view: ImageView, size: Int) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(size)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImageGlideOverride(url: String, view: ImageView, with: Int, height: Int) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(with, height)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImageGlide(url: String, view: ImageView, placeHolder: Int, error: Int) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(placeHolder).error(error)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImageGlideOverride(url: String, view: ImageView, placeHolder: Int, error: Int, size: Int) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(placeHolder).error(error).override(size)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun loadImageGlideOverride(url: String, view: ImageView, placeHolder: Int, error: Int, with: Int, height: Int) {
        Glide.with(App.getAppContext()).load(url).thumbnail(0.1f).apply(RequestOptions().placeholder(placeHolder).error(error).override(with, height)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }

    fun makeToast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun disableAndEnableClick(view: View) {
        view.isClickable = false
        Handler().postDelayed({
            view.isClickable = true
        }, 2000)
    }

    fun openSiteChrome(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            context!!.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            intent.setPackage(null)
            //context!!.startActivity(intent)
            makeToast(getString(R.string.url_not_exist), context!!)
        }
    }

    fun isMyServiceRunning(context: Context, serviceClass: Class<Any>): Boolean {
        var manager: ActivityManager = (context.getSystemService(Context.ACTIVITY_SERVICE) as (ActivityManager))
        for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true
            }
        }
        return false
    }

    fun getRequestCode(): Int {
        val rnd = Random()
        return 100 + rnd.nextInt(900000)
    }



    fun getFileName(uri: Uri): String? {
        if (uri == null) return null
        var fileName: String? = null
        var path = uri.getPath()
        var cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path!!.substring(cut + 1)
        }
        return fileName
    }


    fun parseHtml(text: String): String {

        var result = ""
        var code = false

        for (i in 0 until text.length) {
            if (text[i] == '<') code = true
            if (!code) result = result + text[i]
            if (text[i] == '>') code = false
        }

        return result
    }








}