import React, {StyleHTMLAttributes} from 'react';
import {Question} from "../domain/questions/Question";
import Button from "./Button";
import {Style} from "util";
import config from "../Config";

interface Props {
    media: string,
    style?: React.CSSProperties
}

export default function QuestionMediaBlock(props: Props) {

    const isVideoQuestion = props.media.indexOf(".wmv") > -1;

    return props.media ? (
        <div style={{...(props.style || {}), ...styles.wrapper}}>
            {isVideoQuestion && (
                <video style={{...styles.videoStyles, ...styles.mediaObject}}
                       controls={true}
                       src={config.apiUrl + '/getMedia/' + props.media.replace(".wmv", ".mp4")}
                />
            )}

            {!isVideoQuestion && (
                <img style={{...styles.imgStyles, ...styles.mediaObject}}
                     src={config.apiUrl + '/getMedia/' + props.media}
                />
            )}
        </div>
    ) : (<></>);
}

const styles = {
    wrapper: {
        overflow: 'hidden'
    },

    mediaObject: {
        borderRadius: '4px',
    },

    videoStyles: {
        maxWidth: '100%'
    },

    imgStyles: {
        maxWidth: '100%'
    }
}
