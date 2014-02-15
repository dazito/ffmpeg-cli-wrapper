package net.bramp.ffmpeg.job;

import java.util.List;

import net.bramp.ffmpeg.FFmpeg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

public class TwoPassFFmpegJob extends FFmpegJob {

	final static Logger LOG = LoggerFactory.getLogger(TwoPassFFmpegJob.class);

	final List<String> args1;
	final List<String> args2;

	public TwoPassFFmpegJob(FFmpeg ffmpeg, List<String> args1, List<String> args2) {
		super(ffmpeg);
		this.args1 = args1;
		this.args2 = args2;
	}

	public void run() {
		state = State.RUNNING;

		try {
			ffmpeg.run(args1);
			ffmpeg.run(args2);

			state = State.FINISHED;

		} catch (Throwable t) {
			state = State.FAILED;
			Throwables.propagate(t);
		}
	}
}