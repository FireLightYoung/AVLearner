package ming.com.avlearner.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import ming.com.avlearner.R;
import ming.com.avlearner.base.BaseActivity;
import ming.com.avlearner.utils.LogUtils;
import ming.com.avlearner.utils.SDCardUtils;

/**
 * 参照 https://blog.csdn.net/i_do_can/article/details/53869875
 */
public class AudioRecordActivity extends BaseActivity implements View.OnClickListener {


    public final String pathDir = SDCardUtils.getSDCardPath() + "AVLearner" + File.separator;

    /*录制
      参数配置

     public AudioRecord(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes) throws IllegalArgumentException

             --- audioSource
     该参数指的是音频采集的输入源，可选的值以常量的形式定义在 MediaRecorder.AudioSource 类中，常用的值包括：DEFAULT（默认），VOICE_RECOGNITION（用于语音识别，等同于DEFAULT），MIC（由手机麦克风输入），VOICE_COMMUNICATION（用于VoIP应用）等等。

             --- sampleRateInHz
     采样率，注意，目前44100Hz是唯一可以保证兼容所有Android手机的采样率。

             --- channelConfig
     通道数的配置，可选的值以常量的形式定义在 AudioFormat 类中，常用的是 CHANNEL_IN_MONO（单通道），CHANNEL_IN_STEREO（双通道）

             --- audioFormat
     这个参数是用来配置“数据位宽”的，可选的值也是以常量的形式定义在 AudioFormat 类中，常用的是 ENCODING_PCM_16BIT（16bit），ENCODING_PCM_8BIT（8bit），注意，前者是可以保证兼容所有Android手机的。


             --- bufferSizeInBytes
     这个是最难理解又最重要的一个参数，它配置的是 AudioRecord 内部的音频缓冲区的大小，该缓冲区的值不能低于一帧“音频帧”（Frame）的大小，一帧音频帧的大小计算如下：
     int size = 采样率 x 位宽 x 采样时间 x 通道数

     采样时间一般取 2.5ms~120ms 之间，由厂商或者具体的应用决定，我们其实可以推断，每一帧的采样时间取得越短，产生的延时就应该会越小，当然，碎片化的数据也就会越多。

     在Android开发中，AudioRecord 类提供了一个帮助你确定这个 bufferSizeInBytes 的函数，原型如下：

     int getMinBufferSize(int sampleRateInHz, int channelConfig, int audioFormat);

     强烈建议由该函数计算出需要传入的 bufferSizeInBytes，而不是自己手动计算。

    */
    public final int AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT;
    public final int SAMPLE_RATE_IN_HZ = 44100;
    public final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    public final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public final int BUFFER_SIZE_IN_BYTES = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);

    /*播放
    参数设置

    public AudioTrack(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes, int mode) throws IllegalArgumentException
            --- streamType
    这个参数代表着当前应用使用的哪一种音频管理策略，当系统有多个进程需要播放音频时，这个管理策略会决定最终的展现效果，该参数的可选的值以常量的形式定义在 AudioManager 类中，主要包括：

    STREAM_VOCIE_CALL：电话声音
    STREAM_SYSTEM：系统声音
    STREAM_RING：铃声
    STREAM_MUSCI：音乐声
    STREAM_ALARM：警告声
    STREAM_NOTIFICATION：通知声
            --- sampleRateInHz
    采样率，从AudioTrack源码的“audioParamCheck”函数可以看到，这个采样率的取值范围必须在 4000Hz～192000Hz 之间。

            --- channelConfig
    通道数的配置，可选的值以常量的形式定义在 AudioFormat 类中，常用的是 CHANNEL_IN_MONO（单通道），CHANNEL_IN_STEREO（双通道）

            --- audioFormat
    这个参数是用来配置“数据位宽”的，可选的值也是以常量的形式定义在 AudioFormat 类中，常用的是 ENCODING_PCM_16BIT（16bit），ENCODING_PCM_8BIT（8bit），注意，前者是可以保证兼容所有Android手机的。

            --- bufferSizeInBytes
    配置的是 AudioTrack 内部的音频缓冲区的大小，该缓冲区的值不能低于一帧“音频帧”（Frame）的大小，一帧音频帧的大小计算如下：

    int size = 采样率 x 位宽 x 采样时间 x 通道数

    AudioTrack 类提供了一个帮助你确定这个 bufferSizeInBytes 的函数，原型如下：

    int getMinBufferSize(int sampleRateInHz, int channelConfig, int audioFormat);

            --- mode
    AudioTrack 提供了两种播放模式，一种是 static 方式，一种是 streaming 方式，前者需要一次性将所有的数据都写入播放缓冲区，简单高效，通常用于播放铃声、系统提醒的音频片段; 后者则是按照一定的时间间隔不间断地写入音频数据，理论上它可用于任何音频播放的场景。
    在 AudioTrack 类中，一个是 MODE_STATIC，另一个是 MODE_STREAM
    */
    public final int PLAY_STREAM_TYPE = AudioManager.STREAM_MUSIC;
    public final int PLAY_SAMPLE_RATE_IN_HZ = 44100;
    public final int PLAY_CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_MONO;
    public final int PLAY_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public final int PLAY_BUFFER_SIZE_IN_BYTES = AudioTrack.getMinBufferSize(PLAY_SAMPLE_RATE_IN_HZ, PLAY_CHANNEL_CONFIG, PLAY_AUDIO_FORMAT);
    public final int PLAY_MODE = AudioTrack.MODE_STREAM;

    public boolean mIsRecording = false;
    public boolean mIsPlaying = false;
    public File mAudioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        findViewById(R.id.record_start_btn).setOnClickListener(this);
        findViewById(R.id.record_stop_btn).setOnClickListener(this);
        findViewById(R.id.play_btn).setOnClickListener(this);
        findViewById(R.id.clear_btn).setOnClickListener(this);
        File fileDir = new File(pathDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_start_btn:
                LogUtils.info("开始录制");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            recordStart();
                        } catch (Exception e) {
                            mIsRecording = false;
                            LogUtils.error("开始录制异常!!!");
                        }
                    }
                }.start();

                break;
            case R.id.record_stop_btn:
                LogUtils.info("停止录制");
                recordStop();
                break;
            case R.id.play_btn:
                LogUtils.info("播放");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            play();
                        } catch (Exception e) {
                            mIsPlaying = false;
                            LogUtils.error("开始录制异常!!!");
                        }
                    }
                }.start();

                break;
            case R.id.clear_btn:
                LogUtils.info("清除缓存");
                clear();
                break;
            default:
                break;

        }

    }

    /**
     * 开始录制
     * 需要一个线程不断地从 AudioRecord 的缓冲区将音频数据“读”出来.
     * 注意，这个过程一定要及时，否则就会出现“overrun”的错误，该错误在音频开发中比较常见，
     * 意味着应用层没有及时地“取走”音频数据，导致内部的音频缓冲区溢出。
     */
    public void recordStart() throws Exception {


        long time = System.currentTimeMillis();
        mAudioFile = File.createTempFile(String.valueOf(time), ".pcm", new File(pathDir));//创建临时文件


        AudioRecord record = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_IN_BYTES);

        // 开通输出流到指定的文件
        DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(mAudioFile)));

        // 定义缓冲
        byte[] buffer = new byte[BUFFER_SIZE_IN_BYTES];

        // 开始录制
        record.startRecording();
        mIsRecording = true;


        // 存储录制进度
        int r = 0;

        // 定义循环，根据isRecording的值来判断是否继续录制
        while (mIsRecording) {
            // 从bufferSize中读取字节，返回读取的short个数
            int bufferReadResult = record
                    .read(buffer, 0, buffer.length);
            // 循环将buffer中的音频数据写入到OutputStream中
            if (bufferReadResult > 0) {
                dos.write(buffer, 0, buffer.length);
            }
//            publishProgress(new Integer(r)); // 向UI线程报告当前进度
            LogUtils.info("r==" + r);
            r++; // 自增进度值
        }
        // 录制结束
        record.stop();

        LogUtils.info("mAudioFile.length()==" + mAudioFile.length());


//        mAudioFile.deleteOnExit();//程序退出时删除临时文件
    }

    /**
     * 停止录制
     */
    public void recordStop() {
        mIsRecording = false;

    }

    /**
     * 播放
     */
    public void play() throws Exception {

        // 实例AudioTrack         
        AudioTrack track = new AudioTrack(PLAY_STREAM_TYPE, PLAY_SAMPLE_RATE_IN_HZ, PLAY_CHANNEL_CONFIG, PLAY_AUDIO_FORMAT,
                PLAY_BUFFER_SIZE_IN_BYTES, PLAY_MODE);


    }

    /**
     * 清除缓存
     */
    public void clear() {

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

}
