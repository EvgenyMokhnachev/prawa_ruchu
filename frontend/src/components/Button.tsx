import React from 'react';

interface Props {
    label: string,
    onClick?: () => void,
    style?: React.CSSProperties
}

export default function Button(props: Props) {

    const handleOnClickButton = () => {
        props.onClick && props.onClick();
    }

    return (
        <button style={{
            backgroundColor: 'rgb(78,82,32)',
            color: 'white',
            padding: '4px 8px',
            borderRadius: '3px',
            display: 'inline-block',
            cursor: 'pointer',
            ...(props.style || {})
        }}
        onClick={handleOnClickButton}>
            {props.label}
        </button>
    );
}
