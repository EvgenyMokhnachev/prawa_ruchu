import React from 'react';
import {Language} from "../domain/questions/Language";

interface Props {
    activeLanguage: Language,
    onChange: (language: Language) => void
}

export default function LanguageSwitch(props: Props) {

    return (
        <div style={styles.componentWrapper}>
            <button onClick={() => props.onChange(Language.PL)} style={styles.button} disabled={props.activeLanguage === Language.PL}>PL</button>
            <button onClick={() => props.onChange(Language.EN)} style={styles.button} disabled={props.activeLanguage === Language.EN}>EN</button>
        </div>
    );
}

const styles = {
    componentWrapper: {
        display: 'flex',
        alignItems: 'center',
        margin: '0 8px'
    },

    button: {
        fontSize: '20px',
        color: 'rgb(0,0,0)',
        padding: '4px',
        cursor: 'pointer',
    }
}
